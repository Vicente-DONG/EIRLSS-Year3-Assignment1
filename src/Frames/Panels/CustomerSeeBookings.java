/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Panels;

import Database_managers.DatabaseHelper;
import Frames.logInFrame;
import Managers.CustomerManager;
import Objects.BookingDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CustomerSeeBookings implements ActionListener {

    private JPanel customerSeeBookings;
    private final Font text = new Font("Arial", Font.PLAIN, 14);
    private final Font headers = new Font("Arial", Font.BOLD, 18);
    private CustomerManager customerMgr = CustomerManager.getInstance();
    private JTextArea bookingsDetails;
    private DecimalFormat df = new DecimalFormat("#,##");
    private JCheckBox addSatNavCheck;
    private JCheckBox addBabySeatsCheck;
    private JCheckBox addWineCoolerCheck;
    private JButton backButton;
    private JButton logOffButton;
    private JCheckBox modifyTimeCheck;
    private JComboBox newBookingEndTime;
    private JButton modifyBookingButton;
    private JList displayBookings;
    private JButton cancelBookingButton;
    private BookingDTO booking;
    private DefaultListModel<BookingDTO> bookingsList;

    public CustomerSeeBookings() {
        customerSeeBookings = new JPanel();
        customerSeeBookings.setLayout(new BorderLayout());
        customerSeeBookings.setBorder(new EmptyBorder(20, 15, 0, 15));

        customerSeeBookings.add(userPanel(), BorderLayout.NORTH);
        customerSeeBookings.add(bookingManagementPanel(), BorderLayout.CENTER);
    }

    private JPanel userPanel() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 2));
        userPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton();
        backButton.setText("Back");
        backButton.setFont(text);
        backButton.addActionListener(this);
        backButtonPanel.add(backButton);

        JPanel logOffButtonPanel = new JPanel();
        logOffButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        logOffButton = new JButton();
        logOffButton.setText("Log off");
        logOffButton.setFont(text);
        logOffButton.addActionListener(this);
        logOffButtonPanel.add(logOffButton);

        userPanel.add(backButtonPanel);
        userPanel.add(logOffButtonPanel);

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
        functionsPanel.setLayout(new GridLayout(2, 1));
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
        adminFunctions.setLayout(new GridLayout(2, 1));

        JPanel modificationsPanel = new JPanel();
        modificationsPanel.setLayout(new GridLayout(1, 2));
        JPanel equipmentPanel = new JPanel();
        equipmentPanel.setBorder(BorderFactory.createTitledBorder(null, "Add equipment to your booking", TitledBorder.LEFT, TitledBorder.TOP, text));
        addSatNavCheck = new JCheckBox("Add sat nav to booking");
        addSatNavCheck.setBorder(new EmptyBorder(18, 0, 5, 0));
        addSatNavCheck.setFont(text);
        equipmentPanel.add(addSatNavCheck);

        addBabySeatsCheck = new JCheckBox("Add baby seat(s) to booking");
        addBabySeatsCheck.setFont(text);
        addBabySeatsCheck.setBorder(new EmptyBorder(5, 0, 5, 0));
        equipmentPanel.add(addBabySeatsCheck);

        addWineCoolerCheck = new JCheckBox("Add wine cooler to booking");
        addWineCoolerCheck.setFont(text);
        addWineCoolerCheck.setBorder(new EmptyBorder(5, 0, 5, 0));
        equipmentPanel.add(addWineCoolerCheck);

        JPanel modifyTimePanel = new JPanel();
        modifyTimePanel.setBorder(BorderFactory.createTitledBorder(null, "Modify time of your booking", TitledBorder.LEFT, TitledBorder.TOP, text));
        modifyTimeCheck = new JCheckBox("Do you want to modify return time?");
        modifyTimeCheck.setBorder(new EmptyBorder(40, 0, 20, 60));
        modifyTimeCheck.setFont(text);
        modifyTimeCheck.addActionListener(this);
        modifyTimePanel.add(modifyTimeCheck);

        LocalTime hours[] = {LocalTime.of(8, 00), LocalTime.of(8, 30), LocalTime.of(9, 00), LocalTime.of(9, 30),
            LocalTime.of(10, 00), LocalTime.of(10, 30), LocalTime.of(11, 00), LocalTime.of(11, 30),
            LocalTime.of(12, 00), LocalTime.of(12, 30), LocalTime.of(13, 00), LocalTime.of(13, 30),
            LocalTime.of(14, 00), LocalTime.of(14, 30), LocalTime.of(15, 00), LocalTime.of(15, 30),
            LocalTime.of(16, 00)};
        newBookingEndTime = new JComboBox(hours);
        newBookingEndTime.setPreferredSize(new Dimension(100, 30));
        modifyTimePanel.add(newBookingEndTime);

        modificationsPanel.add(equipmentPanel);
        modificationsPanel.add(modifyTimePanel);

        JPanel buttonsPanel = new JPanel();
        JPanel modifyBookingPanel = new JPanel();
        modifyBookingButton = new JButton("Modify booking");
        modifyBookingButton.setPreferredSize(new Dimension(175, 50));
        modifyBookingButton.setFont(text);
        modifyBookingButton.addActionListener(this);
        modifyBookingPanel.add(modifyBookingButton);
        buttonsPanel.add(modifyBookingPanel);

        JPanel cancelBookingPanel = new JPanel();
        cancelBookingButton = new JButton("Cancel booking");
        cancelBookingButton.setPreferredSize(new Dimension(175, 50));
        cancelBookingButton.setFont(text);
        cancelBookingButton.addActionListener(this);
        cancelBookingPanel.add(cancelBookingButton);
        buttonsPanel.add(cancelBookingPanel);

        adminFunctions.add(modificationsPanel);
        adminFunctions.add(buttonsPanel);

        functionsPanel.add(displayBookingDetailsPanel);
        functionsPanel.add(adminFunctions);

        return functionsPanel;
    }

    private JPanel selectionPanel() {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BorderLayout());

        JLabel companyBookings = new JLabel("Your bookings");
        companyBookings.setFont(headers);
        companyBookings.setHorizontalAlignment(JLabel.CENTER);
        companyBookings.setBorder(new EmptyBorder(15, 0, 15, 0));

        bookingsList = new DefaultListModel<>();
        bookingsList.addAll(customerMgr.getBookingsByCustomerID(customerMgr.getCustomerUser().getCustomerID()));

        displayBookings = new JList(bookingsList);
        displayBookings.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                BookingDTO selectedBooking = (BookingDTO) value;
                setText("'" + selectedBooking.getBookedVehicle().getVehicleName() + "', booked from "
                        + selectedBooking.getBookingStartDay().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                        + " to " + selectedBooking.getBookingEndDay().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
                        + " for " + selectedBooking.getBookingPrice() + "£");

                setFont(new Font("Arial", Font.BOLD, 14));

                return this;
            }
        });
        displayBookings.validate();

        displayBookings.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();

                if (!displayBookings.isSelectionEmpty()) {
                    BookingDTO selected = (BookingDTO) list.getSelectedValue();
                    booking = selected;

                    String outputString = "====================== BOOKING INFORMATION ======================\n"
                            + booking.forCustomerToString()
                            + "\n\n====================== VEHICLE INFORMATION ======================\n"
                            + booking.getBookedVehicle().toString();

                    bookingsDetails.setText(outputString);
                }
            }
        });

        JScrollPane scrollableBookings = new JScrollPane();
        scrollableBookings.setViewportView(displayBookings);

        selectionPanel.add(companyBookings, BorderLayout.NORTH);
        selectionPanel.add(scrollableBookings, BorderLayout.CENTER);

        return selectionPanel;
    }

    public JPanel getCustomerSeeBookings() {
        return customerSeeBookings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            customerSeeBookings.setVisible(false);
            customerSeeBookings.getParent().add(new CustomerMenuPanel().getCustomerMenuPanel());
            customerSeeBookings.getParent().remove(customerSeeBookings);
        } else if (e.getSource() == logOffButton) {
            logInFrame logInFrame = new logInFrame();
            SwingUtilities.getWindowAncestor(customerSeeBookings).dispose();
        } else if (e.getSource() == cancelBookingButton) {
            BookingDTO selectBooking = (BookingDTO) displayBookings.getSelectedValue();
            if (displayBookings.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(customerSeeBookings, "You need to select a booking to cancel", "ERROR - No booking selected", JOptionPane.WARNING_MESSAGE);
            } else if (selectBooking.getBookingStartDay().isAfter(LocalDateTime.now()) && selectBooking.getBookingEndDay().isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(customerSeeBookings, "You can not cancel an ongoing booking", "ERROR - Ongoing booking", JOptionPane.WARNING_MESSAGE);
            } else {
                customerMgr.cancelBooking(selectBooking.getBookingID());
                bookingsList = new DefaultListModel<>();
                bookingsList.addAll(customerMgr.getBookingsByCustomerID(customerMgr.getCustomerUser().getCustomerID()));
                bookingsDetails.setText("Booking's details will go here when selected");

                displayBookings.setModel(bookingsList);
                JOptionPane.showMessageDialog(customerSeeBookings, "Booking canceled!");

            }
        } else if (e.getSource() == modifyBookingButton) {
            if (displayBookings.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(customerSeeBookings, "You need to select a booking to modify", "ERROR - No booking selected", JOptionPane.WARNING_MESSAGE);
            } else {
                BookingDTO bookingToModify = (BookingDTO) displayBookings.getSelectedValue();
                boolean satNavTick = false;
                boolean babySeatstick = false;
                boolean wineCoolerTick = false;
                boolean timeChangetick = false;

                if (addBabySeatsCheck.isSelected()) {
                    babySeatstick = true;
                }
                if (addSatNavCheck.isSelected()) {
                    satNavTick = true;
                }
                if (addWineCoolerCheck.isSelected()) {
                    wineCoolerTick = true;
                }
                if (modifyTimeCheck.isSelected()) {
                    timeChangetick = true;
                }

                if (satNavTick || babySeatstick || wineCoolerTick || timeChangetick) {
                    if (babySeatstick) {
                        bookingToModify.setBabySeatsRequested(babySeatstick);
                    }
                    if (satNavTick) {
                        bookingToModify.setSatNavRequested(satNavTick);
                    }
                    if (wineCoolerTick) {
                        bookingToModify.setWineCoolerRquested(wineCoolerTick);
                    }
                    if (timeChangetick) {
                        LocalTime newSelectedTime = (LocalTime) newBookingEndTime.getSelectedItem();
                        if (newSelectedTime.isBefore(booking.getBookingEndDay().toLocalTime())) {
                            timeChangetick = false;
                            JOptionPane.showMessageDialog(customerSeeBookings, "Unfortunately we cannot remove time from your booking", "ERROR - New booking time before old booking time", JOptionPane.WARNING_MESSAGE);
                        } else {
                            LocalTime extensionTime = (LocalTime) newBookingEndTime.getItemAt(newBookingEndTime.getSelectedIndex());
                            bookingToModify.setBookingEndDay(LocalDateTime.of(bookingToModify.getBookingEndDay().toLocalDate(), extensionTime));
                        }
                    }
                    if (satNavTick || babySeatstick || wineCoolerTick || timeChangetick) {
                        customerMgr.modifyBooking(bookingToModify);
                        booking = bookingToModify;
                        addBabySeatsCheck.setSelected(false);
                        addSatNavCheck.setSelected(false);
                        addWineCoolerCheck.setSelected(false);
                        modifyTimeCheck.setSelected(false);
                        bookingsDetails.setText("====================== BOOKING INFORMATION ======================\n"
                                + booking.forCustomerToString()
                                + "\n\n====================== VEHICLE INFORMATION ======================\n"
                                + booking.getBookedVehicle().toString());
                        JOptionPane.showMessageDialog(customerSeeBookings, "Booking updated!");
                    }
                } else {
                    JOptionPane.showMessageDialog(customerSeeBookings, "You need to select a modification to your booking to do this", "ERROR - No modification selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if (e.getSource() == modifyTimeCheck) {
            BookingDTO bookingToModify = (BookingDTO) displayBookings.getSelectedValue();
            if (customerMgr.allowBookingExtension(bookingToModify.getBookingEndDay())) {
            } else {
                modifyTimeCheck.setSelected(false);
                JOptionPane.showMessageDialog(customerSeeBookings, "Bangers and CO is only able to extend your booking until 4PM of the last day", "ERROR - Unable to extend booking", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
