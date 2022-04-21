package BankManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LogInPage extends JFrame implements ActionListener{

    JButton btn;
    JLabel lbl1, lbl2;
    JTextField txt1, txt2;

    LogInPage(){
        btn = new JButton("Log In");
        lbl1 = new JLabel("username");
        lbl2 = new JLabel("password");
        txt1 = new JTextField(25);
        txt2 = new JTextField(25);

        this.add(lbl1);
        this.add(txt1);
        this.add(lbl2);
        this.add(txt2);
        this.add(btn);
       

        this.setVisible(true);
        this.setSize(400, 400);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        
    }
    
    public static void main(String[]args){
        LogInPage pl = new LogInPage();
    }

    
    
}
