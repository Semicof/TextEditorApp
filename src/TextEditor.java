import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner spinner;
    JLabel fontLabel;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem1;
    JMenuItem menuItem2;
    JMenuItem menuItem3;
    TextEditor()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Cof's Text Editor");
        this.setSize(680,680);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea=new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));

        scrollPane=new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(630,600));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("Font:");
        spinner=new JSpinner();
        spinner.setPreferredSize(new Dimension(50,25));
        spinner.setValue(20);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)spinner.getValue()));
            }
        });

        fontColorButton =new JButton("Color");
        fontColorButton.setBorderPainted(false);
        fontColorButton.addActionListener(this);

        String[] Font=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox=new JComboBox(Font);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        menuBar=new JMenuBar();
        menu=new JMenu("File");
        menuItem1=new JMenuItem("Open");
        menuItem2=new JMenuItem("Save");
        menuItem3=new JMenuItem("Exit");

        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);

        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menuBar.add(menu);


        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(spinner);
        this.add(fontBox);
        this.add(fontColorButton);
        this.add(scrollPane);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== fontColorButton)
        {
            JColorChooser colorChooser=new JColorChooser();
            Color color=colorChooser.showDialog(null,"Choose a color",Color.black);
            textArea.setForeground(color);
        }
        if(e.getSource()== fontBox)
        {
            textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if(e.getSource()== menuItem1)
        {
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter=new FileNameExtensionFilter("Text file","txt");
            fileChooser.setFileFilter(filter);

            int res=fileChooser.showOpenDialog(null);

            if(res==0)
            {
                File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn=null;
                try{
                    fileIn=new Scanner(file);
                    if(file.isFile())
                    {
                        while (fileIn.hasNextLine())
                        {
                            String line=fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                }catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                finally {
                    fileIn.close();
                }

            }
        }
        if(e.getSource()== menuItem2)
        {
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int res=fileChooser.showSaveDialog(null);
            if(res==0)
            {
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try  {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                }catch (FileNotFoundException f)
                {
                    f.printStackTrace();
                }
                finally {
                    fileOut.close();
                }
            }
        }
        if(e.getSource()== menuItem3)
        {
            System.exit(0);
        }
    }
}
