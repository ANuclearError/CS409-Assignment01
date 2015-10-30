package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The KeyConnectDialog allows the user to control the key connects of the model
 * by displaying the gizmos current connections and allowing additions or
 * removals.
 */
public class KeyConnectDialog extends JDialog {
    private static final long serialVersionUID = -7945075082497858941L;

    /**
     * Field for key
     */
    private JTextField keyField;
    /**
     * Label which shows the integer value of key
     */
    private JLabel keyLabel;

    /**
     * Determines that key should be either pressed or released
     */
    private JRadioButton radioBoth;
    /**
     * Determines that key must be pressed
     */
    private JRadioButton radioDown;
    /**
     * Determines that key must be released
     */
    private JRadioButton radioUp;

    /**
     * Button for adding new key bind
     */
    private JButton addButton;
    /**
     * Button for removing existing key bind
     */
    private JButton removeButton;

    /**
     * Contains every key binding of the gizmo as strings
     */
    private JList<String> list;

    /**
     * Contains every key binding of the gizmo as integers
     */
    private Map<Integer, Integer> map;

    /**
     * A flag which indicates if there were any key assignment changes
     */
    private boolean changed;
    /**
     * Current chosen key
     */
    private int currKey;

    /**
     * Creates GUI
     *
     * @param parent
     */
    public KeyConnectDialog(JFrame parent) {
        super(parent);

        createGUI();
    }

    /**
     * Form layout and creates pack()ed window
     */
    private void createGUI() {
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 90, 30 };
        gbl.columnWeights = new double[] { 1d, 1d, 0d, 0d };
        gbl.rowWeights = new double[] { 0d, 0d, 0d, 1d };
        setLayout(gbl);

        GridBagConstraints gbc = new GridBagConstraints();
        // key field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        keyField = new JTextField();
        keyField.addKeyListener(keyListener);
        add(keyField, gbc);
        // ---------
        // key label
        gbc.gridx++;
        gbc.insets = new Insets(5, 5, 0, 0);
        keyLabel = new JLabel("(..)");
        add(keyLabel, gbc);
        // ---------
        // radio both
        gbc.gridx++;
        gbc.insets = new Insets(5, 5, 0, 0);
        radioBoth = new JRadioButton("Both");
        add(radioBoth, gbc);
        // ---------
        // radio down
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 0, 0);
        radioDown = new JRadioButton("Down");
        add(radioDown, gbc);
        // ---------
        // radio up
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 5, 0);
        radioUp = new JRadioButton("Up");
        add(radioUp, gbc);
        // ---------
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioBoth);
        bg.add(radioDown);
        bg.add(radioUp);
        // add button
        gbc.insets = new Insets(5, 5, 0, 5);
        gbc.gridx++;
        gbc.gridy = 0;
        addButton = new JButton("Add key");
        addButton.addActionListener(actionListener);
        add(addButton, gbc);
        // ---------
        // remove button
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy++;
        removeButton = new JButton("Remove key");
        removeButton.addActionListener(actionListener);
        add(removeButton, gbc);
        // ---------
        // list
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.gridwidth = 2;
        list = new JList<String>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(listSelectionListener);
        JScrollPane sp = new JScrollPane(list);
        add(sp, gbc);
        // ---------

        setModalityType(ModalityType.APPLICATION_MODAL);
        setResizable(false);
        reset();
        pack();
    }

    /**
     * Resets dialog state to initial. Should be called every time
     * before setting this dialog visible.
     */
    public void reset() {
        map = new HashMap<Integer, Integer>();
        currKey = -1;
        changed = false;
        keyField.setText("");
        keyLabel.setText("(..)");
        radioBoth.setSelected(true);
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.addElement("No keys assigned");
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        list.setEnabled(false);
        list.setModel(model);
    }

    /**
     * Sets the key map and updates the list
     *
     * @param map Key: a integer value of key.
     * Value: a integer value of state
     * </br><b>0</b> - no state / no assignment,
     * </br><b>1</b> - key pressed / key down,
     * </br><b>2</b> - key released / key up,
     * </br><b>3</b> - both, pressed and released
     */
    public void setKeys(Map<Integer, Integer> map) {
        this.map = map;
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (int key : map.keySet()) {
            String str = getKey(key);
            if ((map.get(key).intValue() & 1) == 1)
                model.addElement(str + " (" + key + ") down");
            if ((map.get(key).intValue() & 2) == 2)
                model.addElement(str + " (" + key + ") up");
        }
        list.setModel(model);

        if (model.isEmpty()) {
            model.addElement("No keys assigned");
            removeButton.setEnabled(false);
            list.setEnabled(false);
        } else
            list.setEnabled(true);

        if (list.getSelectedIndex() == -1)
            removeButton.setEnabled(false);
    }

    /**
     * Indicates if there were any key assignment changes
     * @return <b>true</b> if changed, <b>false</b> otherwise
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Use <code>isChanged()</code> to know if the map was changed
     * @return map of the keys (which may be not changed)
     */
    public Map<Integer, Integer> getMap() {
        return map;
    }

    /**
     * Helper method for getting key as a string
     * @param key integer value of key
     * @return string which represents the key
     */
    private String getKey(int key) {
        switch (key) {
            case 8:
                return "Backspace";
            case 10:
                return "Enter";
            case 32:
                return "Space";
            case 127:
                return "Delete";
            default:
                if (key > 32 && key < 127)
                    return "" + (char) key;
                else
                    return "Unknown key";
        }
    }

    /**
     * Action listener for adding and removing key binding.
     */
    private final ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Add key":
                    if (currKey != -1) {
                        Integer num = map.get(currKey);
                        if (num == null)
                            num = 0;
                        map.put(currKey, num | (radioBoth.isSelected() ? 3 : ( radioDown.isSelected() ? 1 : 2)));
                        changed = true;
                    }
                    break;
                case "Remove key":
                    String value = list.getSelectedValue();
                    if (value != null) {
                        String[] subs = value.split(" ");
                        int key = Integer.parseInt(subs[1].substring(1,
                                subs[1].indexOf(")")));
                        boolean down = subs[2].equals("down");
                        if (down)
                            map.put(key, map.get(key) & 2);
                        else
                            map.put(key, map.get(key) & 1);
                        changed = true;
                    }
                    break;
            }
            setKeys(map);
        }
    };

    /**
     * Listener for better visualization - remove buttons is enabled only when
     * there is something selected in the list
     */
    private final ListSelectionListener listSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            removeButton.setEnabled(true);
        }
    };

    /**
     * Key listener for setting the string for keyField and the currKey variables
     */
    private final KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            e.consume();
            int key = (int) e.getKeyChar();
            currKey = key;
            keyLabel.setText("" + key);
            keyField.setText(getKey(key));
            addButton.setEnabled(true);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
}

/*
================================================================================
Class: KeyConnectDialog
================================================================================
DODGY WARNING!
15 fields
6 methods
================================================================================
Fields
--------------------------------------------------------------------------------
[serialVersionUID = -7945075082497858941L] is primitive.
Might wanna look into this.
[changed] is primitive.
Might wanna look into this.
[currKey] is primitive.
Might wanna look into this.
================================================================================
Constructors
--------------------------------------------------------------------------------
Parameters: [JFrame parent]
================================================================================
Methods
--------------------------------------------------------------------------------
createGUI
DODGY WARNING: 78 lines
GOOD WARNING: 0 params
================================================================================
 */