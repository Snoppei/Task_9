package ru.sc.vsu.berezin_y_a;

import javax.swing.JOptionPane;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class Util {

    private static final Random RND = new Random();

    public static int[] toPrimitive(Integer[] arr) {
        if (arr == null) {
            return null;
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    public static int[] toIntArray(String str) {
        Scanner scanner = new Scanner(str);
        scanner.useLocale(Locale.ROOT);
        scanner.useDelimiter("(\\s|[,;])+");
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }
        Integer[] arr = list.toArray(new Integer[0]);
        return Util.toPrimitive(arr);
    }

    public static int[][] toIntArray2(String[] lines) {
        int[][] arr2 = new int[lines.length][];
        for (int r = 0; r < lines.length; r++) {
            arr2[r] = toIntArray(lines[r]);
        }
        return arr2;
    }

    public static String[] readLinesFromFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines.toArray(new String[0]);
    }

    public static int[][] readIntArray2FromFile(String fileName) {
        try {
            return toIntArray2(readLinesFromFile(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static int[] createRandomIntArray(int length, int minValue, int maxValue) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++)
            arr[i] = minValue + RND.nextInt(maxValue - minValue);
        return arr;
    }

    public static int[][] createRandomIntMatrix(int rowCount, int colCount, int minValue, int maxValue) {
        int[][] matrix = new int[rowCount][];
        for (int r = 0; r < rowCount; r++)
            matrix[r] = createRandomIntArray(colCount, minValue, maxValue);
        return matrix;
    }

    public static int[][] createRandomIntMatrix(int rowCount, int colCount, int maxValue) {
        return createRandomIntMatrix(rowCount, colCount, 0, maxValue);
    }

    public static void showErrorMessageBox(String message, String title, Throwable ex) {
        StringBuilder sb = new StringBuilder(ex.toString());
        if (message != null) {
            sb.append(message);
        }
        if (ex != null) {
            sb.append("\n");
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showErrorMessageBox(String message, Throwable ex) {
        showErrorMessageBox(message, "Ошибка", ex);
    }

    public static void showErrorMessageBox(Throwable ex) {
        showErrorMessageBox(null, ex);
    }

    public static class JTableUtilsException extends ParseException {
        public JTableUtilsException(String s) {
            super(s, 0);
        }
    }

    public static final int DEFAULT_GAP = 6;
    public static final int DEFAULT_COLUMN_WIDTH = 40;
    public static final int DEFAULT_ROW_HEADER_WIDTH = 40;
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final char DELETE_KEY_CHAR_CODE = 127;
    private static final Border DEFAULT_CELL_BORDER = BorderFactory.createEmptyBorder(0, 3, 0, 3);
    private static final Border DEFAULT_RENDERER_CELL_BORDER = DEFAULT_CELL_BORDER;
    private static final Border DEFAULT_EDITOR_CELL_BORDER = BorderFactory.createEmptyBorder(0, 3, 0, 2);
    private static final Map<JTable, Integer> tableColumnWidths = new HashMap<>();

    private static <T extends JComponent> T setFixedSize(T comp, int width, int height) {
        Dimension d = new Dimension(width, height);
        comp.setMaximumSize(d);
        comp.setMinimumSize(d);
        comp.setPreferredSize(d);
        comp.setSize(d);
        return comp;
    }

    private static JButton createPlusMinusButton(String text, int size) {
        JButton button = new JButton(text);
        setFixedSize(button, size, size).setMargin(new Insets(0, 0, 0, 0));
        button.setFocusable(false);
        button.setFocusPainted(false);
        return button;
    }

    private static int getColumnWidth(JTable table) {
        Integer columnWidth = tableColumnWidths.get(table);
        if (columnWidth == null) {
            if (table.getColumnCount() > 0) {
                columnWidth = table.getWidth() / table.getColumnCount();
            } else {
                columnWidth = DEFAULT_COLUMN_WIDTH;
            }
        }
        return columnWidth;
    }

    private static void recalcJTableSize(JTable table) {
        int width = getColumnWidth(table) * table.getColumnCount();
        int height = 0, rowCount = table.getRowCount();
        for (int r = 0; r < rowCount; r++)
            height += table.getRowHeight(height);
        setFixedSize(table, width, height);

        if (table.getParent() instanceof JViewport && table.getParent().getParent() instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            if (scrollPane.getRowHeader() != null) {
                Component rowHeaderView = scrollPane.getRowHeader().getView();
                if (rowHeaderView instanceof JList) {
                    ((JList) rowHeaderView).setFixedCellHeight(table.getRowHeight());
                }
                scrollPane.getRowHeader().repaint();
            }
        }
    }

    private static void addRowHeader(JTable table, TableModel tableModel, JScrollPane scrollPane) {
        final class RowHeaderRenderer extends JLabel implements ListCellRenderer {
            RowHeaderRenderer() {
                JTableHeader header = table.getTableHeader();
                setOpaque(true);
                setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                setHorizontalAlignment(CENTER);
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }

            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index, boolean isSelected, boolean cellHasFocus) {
                setText(String.format("[%d]", index));
                return this;
            }
        }

        ListModel lm = new AbstractListModel() {
            @Override
            public int getSize() {
                return tableModel.getRowCount();
            }

            @Override
            public Object getElementAt(int index) {
                return String.format("[%d]", index);
            }
        };

        JList rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(DEFAULT_ROW_HEADER_WIDTH);
        rowHeader.setFixedCellHeight(
                table.getRowHeight()
        );
        rowHeader.setCellRenderer(new RowHeaderRenderer());

        scrollPane.setRowHeaderView(rowHeader);
        scrollPane.getRowHeader().getView().setBackground(scrollPane.getColumnHeader().getBackground());
    }

    public static void initJTableForArray(
            JTable table, int defaultColWidth,
            boolean showRowsIndexes, boolean showColsIndexes,
            boolean changeRowsCountButtons, boolean changeColsCountButtons,
            int changeButtonsSize, int changeButtonsMargin
    ) {
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        if (!showColsIndexes && table.getTableHeader() != null) {
            table.getTableHeader().setPreferredSize(new Dimension(0, 0));
        }
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setFillsViewportHeight(false);
        table.setDragEnabled(false);
        table.putClientProperty("terminateEditOnFocusLost", true);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"[0]"}, 1) {
            @Override
            public String getColumnName(int index) {
                return String.format("[%d]", index);
            }
        };
        table.setModel(tableModel);
        tableColumnWidths.put(table, defaultColWidth);
        recalcJTableSize(table);

        if (table.getParent() instanceof JViewport && table.getParent().getParent() instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            if (changeRowsCountButtons || changeColsCountButtons) {
                List<Component> linkedComponents = new ArrayList<>();
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                BorderLayout borderLayout = new BorderLayout(changeButtonsMargin, changeButtonsMargin);
                FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
                JPanel panel = new JPanel(borderLayout);
                panel.setBackground(TRANSPARENT);
                if (changeColsCountButtons) {
                    JPanel topPanel = new JPanel(flowLayout);
                    topPanel.setBackground(TRANSPARENT);
                    if (changeRowsCountButtons) {
                        topPanel.add(setFixedSize(new Box.Filler(null, null, null), changeButtonsSize + changeButtonsMargin, changeButtonsSize));
                    }
                    JButton minusButton = createPlusMinusButton("\u2013", changeButtonsSize);
                    minusButton.setName(table.getName() + "-minusColumnButton");
                    minusButton.addActionListener((ActionEvent evt) -> {
                        tableModel.setColumnCount(tableModel.getColumnCount() > 0 ? tableModel.getColumnCount() - 1 : 0);
                        recalcJTableSize(table);
                    });
                    topPanel.add(minusButton);
                    linkedComponents.add(minusButton);
                    topPanel.add(setFixedSize(new Box.Filler(null, null, null), changeButtonsMargin, changeButtonsSize));
                    JButton plusButton = createPlusMinusButton("+", changeButtonsSize);
                    plusButton.setName(table.getName() + "-plusColumnButton");
                    plusButton.addActionListener((ActionEvent evt) -> {
                        tableModel.addColumn(String.format("[%d]", tableModel.getColumnCount()));
                        recalcJTableSize(table);
                    });
                    topPanel.add(plusButton);
                    linkedComponents.add(plusButton);
                    panel.add(topPanel, BorderLayout.NORTH);
                }
                if (changeRowsCountButtons) {
                    JPanel leftPanel = setFixedSize(new JPanel(flowLayout), changeButtonsSize, changeButtonsSize);
                    leftPanel.setBackground(TRANSPARENT);
                    JButton minusButton = createPlusMinusButton("\u2013", changeButtonsSize);
                    minusButton.setName(table.getName() + "-minusRowButton");
                    minusButton.addActionListener((ActionEvent evt) -> {
                        if (tableModel.getRowCount() > 0) {
                            tableModel.removeRow(tableModel.getRowCount() - 1);
                            recalcJTableSize(table);
                        }
                    });
                    leftPanel.add(minusButton);
                    linkedComponents.add(minusButton);
                    leftPanel.add(setFixedSize(new Box.Filler(null, null, null), changeButtonsSize, changeButtonsMargin));
                    JButton plusButton = createPlusMinusButton("+", changeButtonsSize);
                    plusButton.setName(table.getName() + "-plusRowButton");
                    plusButton.addActionListener((ActionEvent evt) -> {
                        tableModel.setRowCount(tableModel.getRowCount() + 1);
                        recalcJTableSize(table);
                    });
                    leftPanel.add(plusButton);
                    linkedComponents.add(plusButton);
                    panel.add(leftPanel, BorderLayout.WEST);
                }
                table.setPreferredScrollableViewportSize(null);
                JScrollPane newScrollPane = new JScrollPane(table);
                newScrollPane.setBackground(scrollPane.getBackground());
                newScrollPane.setBorder(scrollPane.getBorder());
                scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                panel.add(newScrollPane, BorderLayout.CENTER);
                scrollPane.getViewport().removeAll();
                scrollPane.add(panel);
                scrollPane.getViewport().add(panel);
                table.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                    if ("enabled".equals(evt.getPropertyName())) {
                        boolean enabled = (boolean) evt.getNewValue();
                        linkedComponents.forEach((comp) -> comp.setEnabled(enabled));
                        if (!enabled) {
                            table.clearSelection();
                        }
                    }
                });
                linkedComponents.forEach((comp) -> comp.setEnabled(table.isEnabled()));
                scrollPane.setVisible(false);
                scrollPane.setVisible(true);

                scrollPane = newScrollPane;
            }
            table.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                if ("enabled".equals(evt.getPropertyName())) {
                    boolean enabled = (boolean) evt.getNewValue();
                    if (!enabled) {
                        table.clearSelection();
                    }
                } else if ("rowHeight".equals(evt.getPropertyName())) {
                    recalcJTableSize(table);
                }
            });
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    if (evt.getKeyChar() == DELETE_KEY_CHAR_CODE) {
                        for (int r : table.getSelectedRows()) {
                            for (int c : table.getSelectedColumns()) {
                                table.setValueAt(null, r, c);
                            }
                        }
                    }
                }
            });

            table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (comp instanceof JLabel) {
                        JLabel label = (JLabel) comp;
                        label.setHorizontalAlignment((value == null || value.toString().matches("|-?\\d+")) ? RIGHT : LEFT);
                        label.setBorder(DEFAULT_RENDERER_CELL_BORDER);
                    }
                    return comp;
                }
            });
            table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    Component comp = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                    if (comp instanceof JTextField) {
                        JTextField textField = (JTextField) comp;
                        textField.setHorizontalAlignment((value == null || value.toString().matches("|-?\\d+")) ? SwingConstants.RIGHT : SwingConstants.LEFT);
                        textField.setBorder(DEFAULT_EDITOR_CELL_BORDER);
                        textField.selectAll();  // чтобы при начале печати перезаписывать текст
                    }
                    return comp;
                }
            });

            if (showRowsIndexes) {
                addRowHeader(table, tableModel, scrollPane);
            }
        }
    }

    public static void initJTableForArray(
            JTable table, int defaultColWidth,
            boolean showRowsIndexes, boolean showColsIndexes,
            boolean changeRowsCountButtons, boolean changeColsCountButtons
    ) {
        initJTableForArray(
                table, defaultColWidth,
                showRowsIndexes, showColsIndexes, changeRowsCountButtons, changeColsCountButtons,
                22, DEFAULT_GAP
        );
    }

    public static void writeArrayToJTable(JTable table, Object array, String itemFormat) {
        if (!array.getClass().isArray()) {
            return;
        }
        if (!(table.getModel() instanceof DefaultTableModel)) {
            return;
        }
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        tableColumnWidths.put(table, getColumnWidth(table));

        if (itemFormat == null || itemFormat.length() == 0) {
            itemFormat = "%s";
        }
        int rank = 1;
        int len1 = Array.getLength(array), len2 = -1;
        if (len1 > 0) {
            for (int i = 0; i < len1; i++) {
                Object item = Array.get(array, i);
                if (item != null && item.getClass().isArray()) {
                    rank = 2;
                    len2 = Math.max(Array.getLength(item), len2);
                }
            }
        }
        tableModel.setRowCount(rank == 1 ? 1 : len1);
        tableModel.setColumnCount(rank == 1 ? len1 : len2);
        for (int i = 0; i < len1; i++) {
            if (rank == 1) {
                tableModel.setValueAt(String.format(itemFormat, Array.get(array, i)), 0, i);
            } else {
                Object line = Array.get(array, i);
                if (line != null) {
                    if (line.getClass().isArray()) {
                        int lineLen = Array.getLength(line);
                        for (int j = 0; j < lineLen; j++) {
                            tableModel.setValueAt(String.format(itemFormat, Array.get(line, j)), i, j);
                        }
                    } else {
                        tableModel.setValueAt(String.format(itemFormat, Array.get(array, i)), 0, i);
                    }
                }
            }
        }
        recalcJTableSize(table);
    }

    public static void writeArrayToJTable(JTable table, int[] array) {
        writeArrayToJTable(table, array, "%d");
    }

    public static <T> T[][] readMatrixFromJTable(
            JTable table, Class<T> clazz, Function<String, ? extends T> converter,
            boolean errorIfEmptyCell, T emptyCellValue
    ) throws Util.JTableUtilsException {
        TableModel tableModel = table.getModel();
        int rowCount = tableModel.getRowCount(), colCount = tableModel.getColumnCount();
        T[][] matrix = (T[][]) Array.newInstance(clazz, rowCount, colCount);
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                T value = null;
                Object obj = tableModel.getValueAt(r, c);
                if (obj == null || obj instanceof String && ((String) obj).trim().length() == 0) {
                    if (errorIfEmptyCell) {
                        throw new Util.JTableUtilsException(String.format("Empty value on (%d, %d) cell", r, c));
                    } else {
                        value = emptyCellValue;
                    }
                } else {
                    value = converter.apply(obj.toString());
                }
                matrix[r][c] = value;
            }
        }
        return matrix;
    }

    public static int[][] readIntMatrixFromJTable(JTable table) throws ParseException {
        try {
            Integer[][] matrix = readMatrixFromJTable(table, Integer.class, Integer::parseInt, false, 0);
            return (int[][]) Arrays.stream(matrix).map(Util::toPrimitive).toArray((n) -> new int[n][]);
        } catch (Util.JTableUtilsException impossible) {
        }
        return null;
    }

    public static String[] readLinesFromConsole() {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line == null || line.trim().length() == 0)
                break;
            lines.add(line);
        }
        return lines.toArray(new String[0]);
    }

    public static int[][] readIntArray2FromConsole(String arrName, boolean checkMatrix) {
        while (true) {
            try {
                if (arrName == null || arrName.length() == 0) {
                    arrName = "";
                } else {
                    arrName = " " + arrName;
                }
                System.out.printf("Enter 2 array%s:%n", arrName);
                int[][] arr2 = toIntArray2(readLinesFromConsole());
                if (checkMatrix) {
                    for (int i = 1; i < arr2.length; i++)
                        if (arr2[i].length != arr2[0].length) {
                            throw new Exception("Strings have different count of elements");
                        }
                }
                return arr2;
            } catch (Exception e) {
                System.out.print("Вы ошиблись, попробуйте еще раз! ");
            }
        }
    }

    public static int[][] readIntArray2FromConsole(boolean checkMatrix) {
        return readIntArray2FromConsole(null, checkMatrix);
    }

    public static int[][] readIntArray2FromConsole() {
        return readIntArray2FromConsole(false);
    }

    public static int[] readIntArrayFromFile(String fileName) {
        try {
            return toIntArray(readLinesFromFile(fileName)[0]);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String toString(Integer[] arr, String itemFormat) {
        if (arr == null) {
            return null;
        }
        if (itemFormat == null || itemFormat.length() == 0) {
            itemFormat = "%s";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(String.format(Locale.ROOT, itemFormat, arr[i]));
        }
        return sb.toString();
    }

    public static String toString(Integer[][] arr2, String itemFormat) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < arr2.length; r++) {
            if (r > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(toString(arr2[r], itemFormat));
        }
        return sb.toString();
    }

    public static void writeArrayToFile(String fileName, Integer[][] arr2, String itemFormat)
            throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(toString(arr2, itemFormat));
        }
    }

    public static void writeArrayToFile(String fileName, Integer[] arr, String itemFormat)
            throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(toString(arr, itemFormat));
        }
    }

    public static Integer[] toObject(int[] arr) {
        if (arr == null) {
            return null;
        }
        Integer[] result = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    public static void writeArrayToFile(String fileName, Integer[][] arr2)
            throws FileNotFoundException {
        writeArrayToFile(fileName, arr2, null);
    }

    public static int[] intListToArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static void writeArrayToFile(String fileName, Integer[] arr)
            throws FileNotFoundException {
        writeArrayToFile(fileName, arr, null);
    }

    public static void writeListToFile(String fileName, List<Integer> list) throws FileNotFoundException {
        writeArrayToFile(fileName, toObject(intListToArray(list)));
    }

    public static List<Integer> intArrayToList(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            list.add(i, arr[i]);
        }
        return list;
    }

    public static int[] readIntArrayFromJTable(JTable table) throws ParseException {
        int[][] arr2 = readIntMatrixFromJTable1(table);
        assert arr2 != null;
        int[] arr = new int[arr2[0].length];

        for (int i = 0; i < arr.length; i++)
            arr[i] = arr2[0][i];

        return arr;
    }

    public static <T> T[][] readIntMatrixFromJTable(
            JTable table, Class<T> clazz, Function<String, ? extends T> converter,
            boolean errorIfEmptyCell, T emptyCellValue
    ) throws JTableUtilsException {
        TableModel tableModel = table.getModel();
        int rowCount = tableModel.getRowCount(), colCount = tableModel.getColumnCount();
        T[][] matrix = (T[][]) Array.newInstance(clazz, rowCount, colCount);
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                T value = null;
                Object obj = tableModel.getValueAt(r, c);
                if (obj == null || obj instanceof String && ((String) obj).trim().length() == 0) {
                    if (errorIfEmptyCell) {
                        throw new JTableUtilsException(String.format("Empty value on (%d, %d) cell", r, c));
                    } else {
                        value = emptyCellValue;
                    }
                } else {
                    value = converter.apply(obj.toString());
                }
                matrix[r][c] = value;
            }
        }
        return matrix;
    }

    public static int[][] readIntMatrixFromJTable1(JTable table) throws ParseException {
        try {
            Integer[][] matrix = readIntMatrixFromJTable(table, Integer.class, Integer::parseInt, false, 0);
            return (int[][]) Arrays.stream(matrix).map(Util::toPrimitive).toArray((n) -> new int[n][]);
        } catch (JTableUtilsException impossible) {
        }
        return null;
    }

}
