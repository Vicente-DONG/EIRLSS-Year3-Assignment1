/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Panels;

import Frames.logInFrame;
import Managers.CustomerManager;
import Objects.BookingDTO;
import Objects.VehicleDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Date;

/**
 *
 * @author Vicen
 */
public class CustomerBookCarsPanel implements ActionListener {

    private JPanel customerBookCarsPanel;
    private final Font text = new Font("Arial", Font.PLAIN, 14);
    private final Font headers = new Font("Arial", Font.BOLD, 18);
    private CustomerManager customerMgr = CustomerManager.getInstance();
    private String vehicleData;
    private JTextArea displayVehicle;
    private JCheckBox addSatNavCheck;
    private JCheckBox addBabySeatsCheck;
    private JCheckBox addWineCoolerCheck;
    private JCheckBox addLateReturn;
    private JButton backButton;
    private JButton logOffButton;
    private JList displayVehicles;
    private JComboBox bookingStartTime;
    private JComboBox bookingEndTime;
    private JDateChooser bookingStartDate;
    private JDateChooser bookingEndDate;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private long daysBetween;
    private VehicleDTO vehicle;
    private double finalPrice;
    private JTextField finalPriceField;
    private JButton bookButton;
    private JButton checkAvailabilityButton;
    private DefaultListModel<VehicleDTO> JvehicleList;
    private int hourDifferenceValue;
    private boolean availabilityChecked;

    public CustomerBookCarsPanel() {
        customerBookCarsPanel = new JPanel();
        customerBookCarsPanel.setLayout(new BorderLayout());
        customerBookCarsPanel.setBorder(new EmptyBorder(20, 15, 0, 15));

        customerBookCarsPanel.add(userPanel(), BorderLayout.NORTH);
        customerBookCarsPanel.add(displayPanel(), BorderLayout.CENTER);
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

    private JPanel displayPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 2, 0, 30));
        optionsPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        optionsPanel.add(buttonsFunctionPanel());
        optionsPanel.add(selectionPanel());

        return optionsPanel;
    }

    private JPanel functionsPanel() {
        JPanel functionsPanel = new JPanel();
        functionsPanel.setLayout(new BoxLayout(functionsPanel, BoxLayout.Y_AXIS));

        JPanel displayVehiclePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        displayVehicle = new JTextArea();
        displayVehicle.setPreferredSize(new Dimension(600, 325));
        displayVehicle.setMinimumSize(displayVehicle.getPreferredSize());
        displayVehicle.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
        displayVehicle.setText("Selected vehicle information will go here");
        displayVehicle.setEditable(false);
        displayVehicle.setFont(text);

        displayVehiclePanel.add(displayVehicle);

        functionsPanel.add(displayVehiclePanel);

        return functionsPanel;
    }

    private JPanel selectionPanel() {
        JPanel selectionPanel = new JPanel();

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(2, 1));

        JPanel attemptPanel = new JPanel();

        JvehicleList = new DefaultListModel<>();

        if (Period.between(customerMgr.getCustomerUser().getCustomerDOB(), LocalDate.now()).getYears() < 25) {
            for (int i = 0; i < customerMgr.getAllVehiclesForCustomer().size(); i++) {
                if (customerMgr.getAllVehiclesForCustomer().get(i).getVehicleName().equalsIgnoreCase("Small town car")) {
                    JvehicleList.add(i, customerMgr.getAllVehiclesForCustomer().get(i));
                }
            }
        } else {
            JvehicleList.addAll(customerMgr.getAllVehiclesForCustomer());
        }

        displayVehicles = new JList(JvehicleList);
        displayVehicles.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                VehicleDTO vehicle = (VehicleDTO) value;
                setText(vehicle.getVehicleName() + " - Cost per day: " + String.format("%.2f", vehicle.getVehicleRentalperDay()) + "£");

                return this;
            }
        });

        displayVehicles.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                double vehicleRentalPerday = 0;

                Date startDate1 = bookingStartDate.getDate();
                Date endDate1 = bookingEndDate.getDate();

                if (startDate1 == null || endDate1 == null) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Please fill the time slots before choosing a vehicle", "ERROR - Empty date fields", JOptionPane.ERROR_MESSAGE);
                } else {
                    startDate = LocalDateTime.of(bookingStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), (LocalTime) bookingStartTime.getSelectedItem());
                    endDate = LocalDateTime.of(bookingEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), (LocalTime) bookingEndTime.getSelectedItem());
                }
                if (startDate.isAfter(endDate)) {
                    finalPriceField.setText("£0");
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Make sure your start date is before your end date", "ERROR - Start date after end date", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!displayVehicles.isSelectionEmpty()) {
                        vehicle = (VehicleDTO) list.getSelectedValue();
                        vehicleData = vehicle.toString();
                        vehicleRentalPerday = vehicle.getVehicleRentalperDay();
                    }

                    LocalDate startDate = bookingStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate endDate = bookingEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    daysBetween = Period.between(startDate, endDate).getDays();

                    boolean addHalfDay = false;

                    LocalTime endTime = (LocalTime) bookingEndTime.getSelectedItem();
                    LocalTime startTime = (LocalTime) bookingStartTime.getSelectedItem();

                    int hourDifference = endTime.getHour() - startTime.getHour();
                    int minuteDifference = endTime.getMinute() - startTime.getMinute();
                    hourDifferenceValue = Integer.compare(hourDifference, 5);

                    if (hourDifferenceValue == 0 || hourDifferenceValue > 0);
                    {
                        if (hourDifference > 0) {
                            addHalfDay = true;
                        } else {
                            if (minuteDifference >= 0) {
                                addHalfDay = true;
                            }
                        }
                    }
                    finalPrice = customerMgr.calculateBookingPrice(vehicleRentalPerday, daysBetween, addHalfDay);

                    finalPriceField.setText("£" + String.valueOf(finalPrice));

                    displayVehicle.setText(vehicleData);
                }
            }
        }
        );

        JScrollPane scrollableVehicles = new JScrollPane(displayVehicles);

        scrollableVehicles.setViewportView(displayVehicles);

        scrollableVehicles.setPreferredSize(
                new Dimension(600, 350));
        scrollableVehicles.setMinimumSize(scrollableVehicles.getPreferredSize());

        attemptPanel.add(scrollableVehicles);

        informationPanel.add(attemptPanel);

        informationPanel.add(functionsPanel());

        JLabel vehiclesAvailable = new JLabel("Available vehicles");

        vehiclesAvailable.setFont(headers);

        vehiclesAvailable.setHorizontalAlignment(JLabel.CENTER);

        vehiclesAvailable.setBorder(
                new EmptyBorder(5, 0, 15, 0));

        selectionPanel.add(vehiclesAvailable, BorderLayout.NORTH);

        selectionPanel.add(informationPanel);

        return selectionPanel;
    }

    private JPanel buttonsFunctionPanel() {
        JPanel buttonsFunctionPanel = new JPanel();
        buttonsFunctionPanel.setLayout(new BorderLayout());

        LocalTime hours[] = {LocalTime.of(8, 00), LocalTime.of(8, 30), LocalTime.of(9, 00), LocalTime.of(9, 30),
            LocalTime.of(10, 00), LocalTime.of(10, 30), LocalTime.of(11, 00), LocalTime.of(11, 30),
            LocalTime.of(12, 00), LocalTime.of(12, 30), LocalTime.of(13, 00), LocalTime.of(13, 30),
            LocalTime.of(14, 00), LocalTime.of(14, 30), LocalTime.of(15, 00), LocalTime.of(15, 30),
            LocalTime.of(16, 00), LocalTime.of(16, 30), LocalTime.of(17, 00), LocalTime.of(17, 30),
            LocalTime.of(18, 00)};

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(2, 1));

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(new EmptyBorder(40, 0, 0, 0));

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(3, 1));
        timePanel.setBorder(BorderFactory.createTitledBorder(null, "Set start and end time", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)));
        timePanel.setPreferredSize(new Dimension(600, 290));
        timePanel.setMinimumSize(timePanel.getPreferredSize());
        timePanel.setMaximumSize(timePanel.getPreferredSize());

        JPanel hoursPanel = new JPanel();
        hoursPanel.setBorder(new EmptyBorder(40, 0, 0, 0));
        JLabel startTimeLabel = new JLabel("Booking start time: ");
        startTimeLabel.setFont(text);

        bookingStartTime = new JComboBox(hours);
        bookingStartTime.addActionListener(this);

        JLabel endTimeLabel = new JLabel("Booking end time: ");
        endTimeLabel.setFont(text);
        endTimeLabel.setBorder(new EmptyBorder(0, 50, 0, 0));

        bookingEndTime = new JComboBox(hours);
        bookingEndTime.addActionListener(this);

        hoursPanel.add(startTimeLabel);
        hoursPanel.add(bookingStartTime);
        hoursPanel.add(endTimeLabel);
        hoursPanel.add(bookingEndTime);

        JPanel datePanel = new JPanel();
        datePanel.setBorder(new EmptyBorder(10, 0, 20, 0));

        JLabel startDateLabel = new JLabel("Start date: ");
        startDateLabel.setFont(text);

        bookingStartDate = new JDateChooser();
        bookingStartDate.setPreferredSize(new Dimension(170, 25));

        JLabel endDateLabel = new JLabel("End date: ");
        endDateLabel.setFont(text);
        endDateLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        bookingEndDate = new JDateChooser();
        bookingEndDate.setPreferredSize(new Dimension(170, 25));
        JPanel checkAvailabilityPanel = new JPanel();
        checkAvailabilityButton = new JButton("Check availability");
        checkAvailabilityButton.setPreferredSize(new Dimension(200, 50));
        checkAvailabilityButton.addActionListener(this);
        checkAvailabilityButton.setFont(headers);
        checkAvailabilityPanel.add(checkAvailabilityButton);

        datePanel.add(startDateLabel);
        datePanel.add(bookingStartDate);
        datePanel.add(endDateLabel);
        datePanel.add(bookingEndDate);

        timePanel.add(hoursPanel);
        timePanel.add(datePanel);
        timePanel.add(checkAvailabilityPanel);
        optionsPanel.add(timePanel);

        JPanel panel = new JPanel();

        JPanel runningOutOfnames = new JPanel();
        runningOutOfnames.setLayout(new BorderLayout());

        JPanel equipmentPanel = new JPanel();
        equipmentPanel.setLayout(new BoxLayout(equipmentPanel, BoxLayout.Y_AXIS));
        equipmentPanel.setPreferredSize(new Dimension(600, 270));
        equipmentPanel.setMaximumSize(equipmentPanel.getPreferredSize());
        equipmentPanel.setBorder(BorderFactory.createTitledBorder(null, "Add equipment to your booking", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)));

        addSatNavCheck = new JCheckBox("Add sat nav to booking");
        addSatNavCheck.setBorder(new EmptyBorder(23, 0, 5, 0));
        addSatNavCheck.setFont(text);
        addSatNavCheck.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
        equipmentPanel.add(addSatNavCheck);

        addBabySeatsCheck = new JCheckBox("Add baby seat(s) to booking");
        addBabySeatsCheck.setFont(text);
        addBabySeatsCheck.setBorder(new EmptyBorder(30, 0, 5, 0));
        addBabySeatsCheck.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
        equipmentPanel.add(addBabySeatsCheck);

        addWineCoolerCheck = new JCheckBox("Add wine cooler to booking");
        addWineCoolerCheck.setFont(text);
        addWineCoolerCheck.setBorder(new EmptyBorder(30, 0, 5, 0));
        addWineCoolerCheck.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
        equipmentPanel.add(addWineCoolerCheck);

        addLateReturn = new JCheckBox("Add late return");
        addLateReturn.setFont(text);
        addLateReturn.setBorder(new EmptyBorder(30, 0, 5, 0));
        addLateReturn.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
        addLateReturn.addActionListener(this);
        equipmentPanel.add(addLateReturn);

        JPanel finalPricePanel = new JPanel();
        JLabel finalPriceText = new JLabel("Total price: ");
        finalPriceText.setFont(text);
        finalPriceText.setBorder(new EmptyBorder(20, 0, 20, 0));

        finalPriceField = new JTextField("");
        finalPriceField.setFont(headers);
        finalPriceField.setEditable(false);
        finalPriceField.setPreferredSize(new Dimension(150, 40));

        finalPricePanel.add(finalPriceText);
        finalPricePanel.add(finalPriceField);

        JPanel bookVehiclePanel = new JPanel();
        bookButton = new JButton("Book vehicle");
        bookButton.setFont(headers);
        bookButton.setPreferredSize(new Dimension(175, 50));
        bookButton.addActionListener(this);
        bookVehiclePanel.add(bookButton);

        runningOutOfnames.add(equipmentPanel, BorderLayout.CENTER);
        runningOutOfnames.add(finalPricePanel, BorderLayout.SOUTH);
        panel.add(runningOutOfnames);

        detailsPanel.add(optionsPanel);
        detailsPanel.add(panel);

        buttonsFunctionPanel.add(detailsPanel, BorderLayout.CENTER);
        buttonsFunctionPanel.add(bookVehiclePanel, BorderLayout.SOUTH);

        return buttonsFunctionPanel;
    }

    //Method to return JPanel
    public JPanel getCustomerBookCarsPanel() {
        return customerBookCarsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {
            customerBookCarsPanel.setVisible(false);
            customerBookCarsPanel.getParent().add(new CustomerMenuPanel().getCustomerMenuPanel());
            customerBookCarsPanel.getParent().remove(customerBookCarsPanel);
        } else if (e.getSource() == logOffButton) {
            logInFrame logInFrame = new logInFrame();
            SwingUtilities.getWindowAncestor(customerBookCarsPanel).dispose();
        } else if (e.getSource() == addLateReturn) {
            if (customerMgr.customersBookings().size() > 0) {
                if (addLateReturn.isSelected()) {
                    LocalTime hours[] = {LocalTime.of(0, 00), LocalTime.of(0, 30), LocalTime.of(1, 00), LocalTime.of(1, 30),
                        LocalTime.of(2, 00), LocalTime.of(2, 30), LocalTime.of(3, 00), LocalTime.of(3, 30),
                        LocalTime.of(4, 00), LocalTime.of(4, 30), LocalTime.of(5, 00), LocalTime.of(5, 30),
                        LocalTime.of(6, 00), LocalTime.of(6, 30), LocalTime.of(7, 00), LocalTime.of(7, 30),
                        LocalTime.of(8, 00), LocalTime.of(8, 30), LocalTime.of(9, 00), LocalTime.of(9, 30),
                        LocalTime.of(10, 00), LocalTime.of(10, 30), LocalTime.of(11, 00), LocalTime.of(11, 30),
                        LocalTime.of(12, 00), LocalTime.of(12, 30), LocalTime.of(13, 00), LocalTime.of(13, 30),
                        LocalTime.of(14, 00), LocalTime.of(14, 30), LocalTime.of(15, 00), LocalTime.of(15, 30),
                        LocalTime.of(16, 00), LocalTime.of(16, 30), LocalTime.of(17, 00), LocalTime.of(17, 30),
                        LocalTime.of(18, 00), LocalTime.of(18, 30), LocalTime.of(19, 00), LocalTime.of(19, 30),
                        LocalTime.of(20, 00), LocalTime.of(20, 30), LocalTime.of(21, 00), LocalTime.of(21, 30),
                        LocalTime.of(22, 00), LocalTime.of(22, 30), LocalTime.of(23, 00), LocalTime.of(23, 30)};

                    DefaultComboBoxModel<LocalTime> fullDay = new DefaultComboBoxModel<>(hours);
                    bookingEndTime.setModel(fullDay);
                } else {
                    LocalTime hours[] = {LocalTime.of(8, 00), LocalTime.of(8, 30), LocalTime.of(9, 00), LocalTime.of(9, 30),
                        LocalTime.of(10, 00), LocalTime.of(10, 30), LocalTime.of(11, 00), LocalTime.of(11, 30),
                        LocalTime.of(12, 00), LocalTime.of(12, 30), LocalTime.of(13, 00), LocalTime.of(13, 30),
                        LocalTime.of(14, 00), LocalTime.of(14, 30), LocalTime.of(15, 00), LocalTime.of(15, 30),
                        LocalTime.of(16, 00), LocalTime.of(16, 30), LocalTime.of(17, 00), LocalTime.of(17, 30),
                        LocalTime.of(18, 00)};
                    DefaultComboBoxModel<LocalTime> halfDay = new DefaultComboBoxModel<>(hours);
                    bookingEndTime.setModel(halfDay);
                }
            } else {
                addLateReturn.setSelected(false);
                JOptionPane.showMessageDialog(customerBookCarsPanel, "Unfortunately this feature is only available after your first booking", "ERROR - Feature unavailable", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == bookButton) {
            if (bookingStartDate.getDate() == null || bookingEndDate.getDate() == null) {
                JOptionPane.showMessageDialog(customerBookCarsPanel, "Please fill the time slots before choosing a vehicle", "ERROR - Empty date fields", JOptionPane.ERROR_MESSAGE);
            } else if (displayVehicles.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(customerBookCarsPanel, "Please select a vehicle to check availability", "ERROR - No vehicle selected", JOptionPane.ERROR_MESSAGE);
            } else {
                LocalDate sDate = bookingStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate eDate = bookingEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                startDate = LocalDateTime.of(sDate, (LocalTime) bookingStartTime.getSelectedItem());
                endDate = LocalDateTime.of(eDate, (LocalTime) bookingEndTime.getSelectedItem());
                daysBetween = Duration.between(startDate, endDate).toDays();

                if (bookingStartDate.getDate().toString().equalsIgnoreCase("") || bookingEndDate.getDate().toString().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Please fill all fields before attempting to book a vehicle", "ERROR - Empty date fields", JOptionPane.ERROR_MESSAGE);
                } else if (startDate.isAfter(endDate)) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Make sure your start date is before your end date", "ERROR - Start date after end date", JOptionPane.ERROR_MESSAGE);
                } else if (Duration.between(startDate, endDate).toHours() < 5) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "You can not book a vehicles for less than 5 hours", "ERROR - Booking too short", JOptionPane.ERROR_MESSAGE);
                } else if (Duration.between(startDate, endDate).toDays() > 14) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "You can not book a vehicles for more than 2 weeks", "ERROR - Booking too long", JOptionPane.ERROR_MESSAGE);
                } else if (availabilityChecked == false) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Please check availability before you book a vehicle", "ERROR - Availability not checked", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean satNavTick = false;
                    boolean babySeatstick = false;
                    boolean wineCoolerTick = false;
                    boolean lateReturnTick = false;

                    if (addBabySeatsCheck.isSelected()) {
                        babySeatstick = true;
                    }
                    if (addSatNavCheck.isSelected()) {
                        satNavTick = true;
                    }
                    if (addWineCoolerCheck.isSelected()) {
                        wineCoolerTick = true;
                    }
                    if (addLateReturn.isSelected()) {
                        lateReturnTick = true;
                    }

                    BookingDTO newBooking = new BookingDTO((VehicleDTO) displayVehicles.getSelectedValue(), customerMgr.getCustomerUser(), startDate, endDate, finalPrice, satNavTick, babySeatstick, wineCoolerTick, lateReturnTick, false);
                    boolean successful = customerMgr.createBooking(newBooking);
                    displayVehicles.clearSelection();
                    if (successful) {
                        JOptionPane.showMessageDialog(customerBookCarsPanel, "Vehicle booked!");
                        availabilityChecked = false;
                    } else {
                        JOptionPane.showMessageDialog(customerBookCarsPanel, "Something wrong happened, contact the office", "ERROR - Whoops", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else if (e.getSource() == checkAvailabilityButton) {
            availabilityChecked = false;
            
            displayVehicles.clearSelection();
            if (bookingStartDate.getDate() == null || bookingEndDate.getDate() == null) {
                JOptionPane.showMessageDialog(customerBookCarsPanel, "Please fill all fields before attempting to book a vehicle", "ERROR - Empty date fields", JOptionPane.ERROR_MESSAGE);
            } else {
                if (bookingStartDate.getDate().toString().equalsIgnoreCase("") || bookingEndDate.getDate().toString().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(customerBookCarsPanel, "Please fill all fields before attempting to check for availability", "ERROR - Empty date fields", JOptionPane.ERROR_MESSAGE);
                } else {
                    LocalDate sDate = bookingStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate eDate = bookingEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    startDate = LocalDateTime.of(sDate, (LocalTime) bookingStartTime.getSelectedItem());
                    endDate = LocalDateTime.of(eDate, (LocalTime) bookingEndTime.getSelectedItem());

                    ArrayList<VehicleDTO> vehiclesAvailabe = customerMgr.vehiclesAvailable(startDate, endDate);

                    DefaultListModel<VehicleDTO> newList = new DefaultListModel<>();

                    if (Period.between(customerMgr.getCustomerUser().getCustomerDOB(), LocalDate.now()).getYears() < 25) {
                        for (int i = 0; i < vehiclesAvailabe.size(); i++) {
                            if (vehiclesAvailabe.get(i).getVehicleName().equalsIgnoreCase("Small town car")) {
                                newList.add(i, vehiclesAvailabe.get(i));
                            }
                        }

                        displayVehicles.setModel(newList);
                    } else {
                        newList.addAll(vehiclesAvailabe);
                        displayVehicles.setModel(newList);
                    }

                    availabilityChecked = true;
                }
            }
        }
    }

//    @Override
//    public void propertyChange(PropertyChangeEvent e) {
//        if(e.getSource() == bookingStartDate || e.getSource() == bookingEndDate)
//        {
//            if(!bookingStartDate.getDate().toString().equalsIgnoreCase("") && !bookingEndDate.getDate().toString().equalsIgnoreCase(""))
//            {
//                startDate = bookingStartDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                endDate = bookingEndDate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                
//                daysBetween = Period.between(startDate, endDate).getDays();
//                
//                finalPrice = daysBetween * vehicle.getVehicleRentalperDay();
//                
//                finalPriceField.setText(df.format(finalPrice));
//            }
//        }
//    }
}
