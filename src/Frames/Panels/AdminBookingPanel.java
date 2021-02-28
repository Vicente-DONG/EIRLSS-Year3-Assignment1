package Frames.Panels;

import Frames.logInFrame;
import Managers.AdminManager;
import Objects.BookingDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AdminBookingPanel implements ActionListener {

    private JPanel AdminBookingPanel;
    private final Font text = new Font("Arial", Font.PLAIN, 14);
    private final Font headers = new Font("Arial", Font.BOLD, 18);
    private AdminManager adminMgr = AdminManager.getInstance();
    private JTextArea bookingsDetails;
    private JButton confirmRetrievalButton;
    private JButton blacklistUserButton;
    private BookingDTO booking;
    private JList bookingsDisplay;
    private JButton logOffButton;

    public AdminBookingPanel() {
        AdminBookingPanel = new JPanel();
        AdminBookingPanel.setLayout(new BorderLayout());
        AdminBookingPanel.setBorder(new EmptyBorder(20, 15, 0, 15));

        AdminBookingPanel.add(userPanel(), BorderLayout.NORTH);
        AdminBookingPanel.add(bookingManagementPanel(), BorderLayout.CENTER);
    }

    private JPanel userPanel() {
        JPanel userPanel = new JPanel();

        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

        JLabel welcomeMessage = new JLabel("Welcome back, " + adminMgr.getAdminUser().getUsername());
        welcomeMessage.setFont(headers);
        welcomeMessage.setBorder(new EmptyBorder(0, 0, 0, 1050));

        logOffButton = new JButton();
        logOffButton.setText("Log off");
        logOffButton.setFont(text);
        logOffButton.addActionListener(this);

        userPanel.add(welcomeMessage);
        userPanel.add(logOffButton);

        return userPanel;
    }

    private JPanel bookingManagementPanel() {
        JPanel bookingManagementPanel = new JPanel();
        bookingManagementPanel.setLayout(new GridLayout(1, 2));
        bookingManagementPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        bookingManagementPanel.add(functionsPanel());
        bookingManagementPanel.add(selectionPanel());

        return bookingManagementPanel;
    }

    private JPanel functionsPanel() {
        JPanel functionsPanel = new JPanel();
        functionsPanel.setLayout(new GridLayout(2, 2));
        functionsPanel.setBorder(new EmptyBorder(47, 0, 0, 0));

        JPanel displayBookingDetailsPanel = new JPanel();

        bookingsDetails = new JTextArea("Booking's details will go here when selected");
        bookingsDetails.setEditable(false);
        bookingsDetails.setFont(text);

        JScrollPane bookingDetailsScroll = new JScrollPane(bookingsDetails);
        bookingDetailsScroll.setPreferredSize(new Dimension(630, 340));
        bookingDetailsScroll.setMinimumSize(bookingsDetails.getPreferredSize());
        bookingDetailsScroll.setViewportView(bookingsDetails);

        displayBookingDetailsPanel.add(bookingDetailsScroll);

        JPanel adminFunctions = new JPanel();
        adminFunctions.setLayout(new GridLayout(2, 2));

        JPanel confirmRetrievalPanel = new JPanel();
        confirmRetrievalPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
        confirmRetrievalButton = new JButton("Vehicle retrieved");
        confirmRetrievalButton.setPreferredSize(new Dimension(175, 50));
        confirmRetrievalButton.setFont(text);
        confirmRetrievalButton.addActionListener(this);
        confirmRetrievalPanel.add(confirmRetrievalButton);

        JPanel blackListUserPanel = new JPanel();
        blackListUserPanel.setBorder(new EmptyBorder(0, 0, 0, 100));
        blacklistUserButton = new JButton("Blacklist this user");
        blacklistUserButton.setPreferredSize(new Dimension(175, 50));
        blacklistUserButton.setFont(text);
        blacklistUserButton.addActionListener(this);
        blackListUserPanel.add(blacklistUserButton);

        adminFunctions.add(new JPanel());
        adminFunctions.add(new JPanel());
        adminFunctions.add(confirmRetrievalPanel);
        adminFunctions.add(blackListUserPanel);

        functionsPanel.add(displayBookingDetailsPanel);
        functionsPanel.add(adminFunctions);

        return functionsPanel;
    }

    private JPanel selectionPanel() {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BorderLayout());

        JLabel companyBookings = new JLabel("Company's bookings");
        companyBookings.setFont(headers);
        companyBookings.setHorizontalAlignment(JLabel.CENTER);
        companyBookings.setBorder(new EmptyBorder(15, 0, 15, 0));

        DefaultListModel<BookingDTO> bookingsList = new DefaultListModel<>();
        bookingsList.addAll(adminMgr.getAllBookings());

        bookingsDisplay = new JList(bookingsList);
        bookingsDisplay.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                BookingDTO booking = (BookingDTO) value;
                setText("ID: " + booking.getBookingID() + ", '" + booking.getBookedVehicle().getVehicleName()
                        + "' by " + booking.getBookingCustomer().getCustomerFirstName() + " "
                        + booking.getBookingCustomer().getCustomerLastName());

                setFont(new Font("Arial", Font.BOLD, 14));

                return this;
            }
        });

        bookingsDisplay.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();

                BookingDTO selectedBooking = (BookingDTO) list.getSelectedValue();
                booking = selectedBooking;
                String outputString = "====================== BOOKING INFORMATION ======================\n\n"
                        + booking.toString()
                        + "\n\n====================== VEHICLE INFORMATION ======================\n\n"
                        + booking.getBookedVehicle().toString()
                        + "\n\n====================== CUSTOMER INFORMATION ======================\n\n"
                        + booking.getBookingCustomer().toString();

                bookingsDetails.setText(outputString);
                bookingsDetails.validate();
            }
        });

        JScrollPane scrollableBookings = new JScrollPane();
        scrollableBookings.setViewportView(bookingsDisplay);

        selectionPanel.add(companyBookings, BorderLayout.NORTH);
        selectionPanel.add(scrollableBookings, BorderLayout.CENTER);

        return selectionPanel;
    }

    public JPanel getAdminBookingPanel() {
        return AdminBookingPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmRetrievalButton) {
            if (bookingsDisplay.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(AdminBookingPanel, "Please select a vehicle first", "ERROR - No vehicle selected", JOptionPane.ERROR_MESSAGE);
            } else {
                int answer = JOptionPane.showConfirmDialog(AdminBookingPanel, "Are you sure you want to set this booking as retrieved?", "CONFIRMATION - Mark as retrieved", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    booking = adminMgr.updateBooking(booking);
                    JOptionPane.showMessageDialog(AdminBookingPanel, "Vehicle has been marked as retrieved");

                    bookingsDetails.setText("====================== BOOKING INFORMATION ======================\n\n"
                            + booking.toString()
                            + "\n\n====================== VEHICLE INFORMATION ======================\n\n"
                            + booking.getBookedVehicle().toString()
                            + "\n\n====================== CUSTOMER INFORMATION ======================\n\n"
                            + booking.getBookingCustomer().toString());
                }
            }
        } else if (e.getSource() == blacklistUserButton) {
            if (bookingsDisplay.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(AdminBookingPanel, "Please select a booking first", "ERROR - No booking selected", JOptionPane.ERROR_MESSAGE);
            } else {
                int answer = JOptionPane.showConfirmDialog(AdminBookingPanel, "Are you sure you want to blacklist this user?", "CONFIRMATION - Mark as blacklisted", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    booking.setBookingCustomer(adminMgr.blackListCustomer(booking.getBookingCustomer()));
                    JOptionPane.showMessageDialog(AdminBookingPanel, "Chosen customer has been blacklisted");

                    bookingsDetails.setText("====================== BOOKING INFORMATION ======================\n\n"
                            + booking.toString()
                            + "\n\n====================== VEHICLE INFORMATION ======================\n\n"
                            + booking.getBookedVehicle().toString()
                            + "\n\n====================== CUSTOMER INFORMATION ======================\n\n"
                            + booking.getBookingCustomer().toString());
                }
            }
        } else if (e.getSource() == logOffButton) {
            logInFrame logInFrame = new logInFrame();
            SwingUtilities.getWindowAncestor(AdminBookingPanel).dispose();
        }
    }
}
