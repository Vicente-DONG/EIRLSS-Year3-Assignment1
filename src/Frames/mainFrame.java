/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Frames.Panels.AdminBookingPanel;
import Frames.Panels.CustomerMenuPanel;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Vicen
 */
public class mainFrame extends JFrame implements ActionListener{
    private GraphicsConfiguration gc;
    private JFrame mainFrame;
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public mainFrame(boolean isCustomer)
    {
        mainFrame = new JFrame(gc);
        mainFrame.setTitle("Bang and Co vehicle renting company");
        mainFrame.setSize(1400, 900);
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2, dim.height / 2 - mainFrame.getSize().height / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        if(isCustomer == true)
        {
            mainFrame.getContentPane().add(new CustomerMenuPanel().getCustomerMenuPanel());
        } else {
            mainFrame.getContentPane().add(new AdminBookingPanel().getAdminBookingPanel());
        }

        mainFrame.setVisible(true);
    }

    public void closeFrame()
    {
        mainFrame.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
