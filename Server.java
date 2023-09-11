
package chattingapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server  implements ActionListener{
    JTextField text;
    JPanel a1;
     static Box vertical= Box.createVerticalBox();
     static JFrame f=new JFrame();
    static  DataOutputStream dout;
    Server()
    {
        f.setLayout(null);
        
        JPanel p1=new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
       f. add(p1);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                System.exit(0);
            }
        });
        
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        
        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(350,20,30,30);
        p1.add(phone);
        
        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);
        JLabel morevert=new JLabel(i15);
        morevert.setBounds(400,20,10,25);
        p1.add(morevert);
        
        JLabel name=new JLabel("Manasa Panda");
        name.setBounds(100,20,150,20);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        
        JLabel Status=new JLabel("Active Now");
        Status.setBounds(100,40,100,20);
        Status.setForeground(Color.white);
        Status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(Status);
        
        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);
        
        text=new JTextField();
        text.setBounds(5,650,310,40);
        text.setFont(new Font("SEN_SERIF",Font.ITALIC,16));
        f.add(text);
        
        JButton send=new JButton("Send");
        send.setBounds(320,650,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SEN_SERIF",Font.BOLD,16));
        f.add(send);
        
        f.setTitle("Server");
       f.setSize(450,700);
        f.setLocation(200,20);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) 
    {
        String out=text.getText();
        
       
        JPanel p2=formatLabel(out);
        
        a1.setLayout(new BorderLayout());//set layout as borderLayout as we have to insert our messages in border
        
        JPanel right=new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);//to place messages at end of string
        
        vertical.add(right);//used for multiple text upon one another vertically
        vertical.add(Box.createVerticalStrut(15));//to add space between lines
        
        
        a1.add(vertical,BorderLayout.PAGE_START);
        
        try {
            dout.writeUTF(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        text.setText("");
        
        
        f.repaint();
        f.invalidate();
        f.validate();
        
    }
    
    public static JPanel formatLabel(String out)
    {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output=new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");//to take a fixed background size for all messages
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));//to change backgrount color of message
        output.setOpaque(true);//this function is used to do change in color by default false we need to change it to true
        output.setBorder(new EmptyBorder(15,15,15,50));//to add text padding
        
        panel.add(output);
        
       Calendar cal=Calendar.getInstance();//create instance of calendar
       SimpleDateFormat sdf= new SimpleDateFormat("HH:mm");//Specify formate of date
       
       JLabel time =new JLabel();
       time.setText(sdf.format(cal.getTime()));//to get current time
       
       panel.add(time);//to add time  to panel 
        
        return panel;
    }
    
    public static void main(String arg[])
    {
        new Server();
        
        try{
            ServerSocket skt=new ServerSocket(6002);//create a server
            while(true)
            {
                Socket s=skt.accept();//to accept all messages
                DataInputStream din=new DataInputStream(s.getInputStream());//to receive/get messages
                dout=new DataOutputStream(s.getOutputStream());//to forward/send messages
                
                while(true)
                {
                    String msg=din.readUTF();//to recive messages from client till client send 
                    JPanel panel=formatLabel(msg);
                    
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
