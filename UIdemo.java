

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UIdemo extends JFrame {
    public static String ri = "";
    private static UIdemo single = null;
    PrintStream redirect = new PrintStream(new RedirectOutputStream());
    public static Main main = new Main();

    JMenuBar menuBar = new JMenuBar();
    JMenu files = new JMenu("�ļ�(F)");
    JMenuItem newFile = new JMenuItem("�½�(N)");
    JMenuItem open = new JMenuItem("��(O)...");
    JMenuItem save = new JMenuItem("����(S)");
    JMenuItem saveAs = new JMenuItem("���Ϊ(A)...");
    JMenuItem exit = new JMenuItem("�˳�(X)");
    JButton runButton = new JButton();
    JToolBar runBar = new JToolBar();
    RSyntaxTextArea inputText;
    RSyntaxTextArea outputText;

    String openedPath = "";
    boolean opened = false;

    public static void waitI(){
        try {
            main.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static UIdemo getInstance() {
        if (single == null) {
            single = new UIdemo();
        }
        return single;
    }
    public RSyntaxTextArea getInputAre(){
        RTextScrollPane rr = (RTextScrollPane)this.getContentPane().getComponent(0);
        RSyntaxTextArea rt = (RSyntaxTextArea)rr.getTextArea();
        return rt;
    }
    public RSyntaxTextArea getOutputAre(){
        RTextScrollPane rr = (RTextScrollPane)this.getContentPane().getComponent(2);
        RSyntaxTextArea rt = (RSyntaxTextArea)rr.getTextArea();
        return rt;
    }
    public UIdemo() {
        System.setOut(redirect);
        System.setErr(redirect);
        JPanel cp = new JPanel(new BorderLayout());
        init();
        RSyntaxTextArea textArea = new RSyntaxTextArea(30, 120);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        sp.setFoldIndicatorEnabled(true);

        RSyntaxTextArea textArea2 = new RSyntaxTextArea(15, 120);

        textArea2.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
        textArea2.setCodeFoldingEnabled(true);
        RTextScrollPane sp2 = new RTextScrollPane(textArea2);
        sp2.setLineNumbersEnabled(false);
        cp.add(sp, BorderLayout.CENTER);
        cp.add(runBar,BorderLayout.NORTH);
        cp.add(sp2,BorderLayout.SOUTH);
        setContentPane(cp);
        setJMenuBar(menuBar);
        setTitle("CMM Interpreter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

    }

    void init(){
        files.setMnemonic('F');
        newFile.setMnemonic('N');
        open.setMnemonic('O');
        save.setMnemonic('S');
        saveAs.setMnemonic('A');
        exit.setMnemonic('X');
        ImageIcon icon = new ImageIcon("img/ic_run.png");
        runButton = new JButton(icon);
        runBar.add(runButton);

        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));

        files.add(newFile);
        files.add(open);
        files.add(save);
        files.add(saveAs);
        files.addSeparator();
        files.add(exit);
        menuBar.add(files);
        //���ò˵���
        setJMenuBar(menuBar);
        //�¼���Ӽ�����
        Listen listen = new Listen();
        ListenMouse listen_mouse = new ListenMouse();
        open.addActionListener(listen);
        save.addActionListener(listen);
        saveAs.addActionListener(listen);
        exit.addActionListener(listen);
        runButton.addMouseListener(listen_mouse);


    }

    class Listen implements ActionListener
    {
        //ʵ�����ڶ��ļ����в������¼�������
        public void actionPerformed(ActionEvent e)
        {
            inputText = single.getInputAre();
            Object source = e.getSource();
            //���ļ��¼�
            if (source == open)
            {
                FileDialog openFile = new FileDialog(single, "���ļ�...", FileDialog.LOAD);
                openFile.setVisible(true);
                String filePath = openFile.getDirectory() + openFile.getFile();
                try
                {
                    FileInputStream fis = new FileInputStream(filePath);
                    byte[] content = new byte[fis.available()];
                    fis.read(content);
                    inputText.setText(new String(content));
                    inputText.setCaretPosition(0);
                    if (openFile.getFile() != null)
                    {
//                        th.setTitle(openFile.getFile() + name);
                        openedPath = filePath;
                        opened = true;
                    }
                    fis.close();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                opened = true;
            }
//���漰���Ϊ�¼�
            else if (source == save || source == saveAs)
            {
                String savePath = openedPath;
                if (savePath == null || source == saveAs)
                {
                    FileDialog saveFile = new FileDialog(single, "�����ļ�...", FileDialog.SAVE);
                    saveFile.setVisible(true);
                    savePath = saveFile.getDirectory() + saveFile.getFile();
                }
                try
                {
                    FileOutputStream fos = new FileOutputStream(savePath);
                    fos.write(inputText.getText().getBytes());
                    fos.close();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                if (source == save)
                    openedPath = savePath;
            }
            else if (source == exit)
            {
                System.exit(0);
            }
        }
    }

    class ListenMouse implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            UIdemo demo = UIdemo.getInstance();
            RSyntaxTextArea ot = demo.getOutputAre();
            ot.removeAllLineHighlights();
            ot.setText("");

            RSyntaxTextArea rt = demo.getInputAre();
            String content = rt.getText();
            ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes());
            ANTLRInputStream input = null;
            try {
                input = new ANTLRInputStream(stream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            CMMLexer lexer = new CMMLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CMMParser parser = new CMMParser(tokens);
            ParseTreeWalker walker = new ParseTreeWalker();
            ParseTree tree = parser.program();
            DefPhase def = new DefPhase();
            walker.walk(def, tree);
            RefPhase ref = new RefPhase(def.globals, def.scopes, def.types);
            walker.walk(ref, tree);
//          
            if (ref.error) {
                //ֹͣ
            } else if (parser.getNumberOfSyntaxErrors() < 1) {
                
                main = new Main();
                main.setInput(tree);
//              
                main.start();
            }
        }
        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {

        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {

        }
    }

    public static void main(String[] args) {
        // Start all Swing applications on the EDT.
        UIdemo td = UIdemo.getInstance();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIdemo td = UIdemo.getInstance();
                td.setVisible(true);
            }
        });
    }

    public class RedirectOutputStream extends OutputStream {
        public void write(int arg0) throws IOException {
            // д��ָ�����ֽڣ�����
        }

        public void write(byte data[]) throws IOException{
            // ׷��һ���ַ���
            getOutputAre().append(new String(data));
        }

        public void write(byte data[], int off, int len) throws IOException {
            // ׷��һ���ַ�����ָ���Ĳ��֣��������Ҫ
            getOutputAre().append(new String(data, off, len));
            // �ƶ�TextArea�Ĺ�굽���ʵ���Զ�����
            getOutputAre().setCaretPosition(getOutputAre().getText().length());
        }
    }

}