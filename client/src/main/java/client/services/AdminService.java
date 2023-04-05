package client.services;

import commons.Board;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    /**
     * remove any duplicate columns created by parsing the map sent by SQL
     * @param tableView the table instance
     */
    public void removeDuplicateColumns(TableView<?> tableView) {
        List<TableColumn<?, ?>> columnsToRemove = new ArrayList<>();
        Set<String> columnNames = new HashSet<>();

        for (TableColumn<?, ?> column : tableView.getColumns()) {
            if (columnNames.contains(column.getText())) {
                columnsToRemove.add(column);
            } else {
                columnNames.add(column.getText());
            }
        }

        tableView.getColumns().removeAll(columnsToRemove);
    }


    /**
     * get a column instance from a table
     * if it does not exist, create it
     * @param table the table instance
     * @param columnName the column name to search for
     * @return the column element,
     * if found the currently existing one,
     * else a new one for this table
     */
    public TableColumn<Map<String, Object>, Object> findColumn(
            TableView<Map<String, Object>> table, String columnName) {
        // Look for existing column with matching name
        for (TableColumn<Map<String, Object>, ?> column : table.getColumns()) {
            if (column.getText().equals(columnName)) {
                return (TableColumn<Map<String, Object>, Object>) column;
            }
        }
        // If no existing column found, create a new one
        TableColumn<Map<String, Object>, Object> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get(columnName)));
        return newColumn;
    }

    /**
     * this method is my lowest point
     * @param item the string from the list view
     * @param boardElementList list of board instances on the page
     * @return the board instance
     */
    public Board getBoardFromString(String item, List<Board> boardElementList) {
        for (Board b : boardElementList) {
            String s = "Board #"+b.getId();
            s+="\nName: "+b.getName();
            s+="\nKey: " +b.getBoardkey();
            if (s.equals(item)) return b;
        }
        return null;
    }



    /**
     * check if a column exists in a table
     * @param table the table instance
     * @param columnName the column's name
     * @return T/F does it exist?
     */
    public boolean doesColumnExist(TableView<?> table, String columnName) {
        ObservableList<? extends TableColumn<?, ?>> columns = table.getColumns();
        for (TableColumn<?, ?> column : columns) {
            if (column.getText().equals(columnName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Generate a table to display SQL output
     * @param outputTable the List<Map<String, Object>> that sql returns
     * @return the TableView instance
     */
    public TableView<Map<String, Object>> generateTable(List<Map<String, Object>> outputTable) {
        //for (List<Map<String, Object>> outputTable : res) { //todo multiple queries at once
        TableView<Map<String, Object>> table = new TableView<>();
        ObservableList<Map<String, Object>> tableData = FXCollections.observableArrayList(outputTable);
        table.setItems(tableData);
        for (Map<String, Object> t : outputTable) {
            for (String column : t.keySet()) {
                TableColumn<Map<String, Object>, Object> tableColumn = findColumn(table, column);
                tableColumn.setCellValueFactory(cellData ->
                        new SimpleObjectProperty<>(cellData.getValue().get(column)));
                if (!doesColumnExist(table, tableColumn.getText())) table.getColumns().add(tableColumn);
            }
            removeDuplicateColumns(table);
            List<TableColumn<Map<String, Object>, ?>> columnsToRemove = new ArrayList<>();
            for (TableColumn<Map<String, Object>, ?> column : table.getColumns()) {
                boolean columnIsEmpty = true;
                for (Map<String, Object> row : table.getItems()) {
                    if (row.get(column.getText()) != null) {
                        columnIsEmpty = false;
                        break;
                    }
                }
                if (columnIsEmpty) {
                    columnsToRemove.add(column);
                }
            }
            // remove empty columns
            table.getColumns().removeAll(columnsToRemove);
        }
        return table;
    }

}
