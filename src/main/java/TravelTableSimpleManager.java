import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;

import java.util.List;

public class TravelTableSimpleManager extends SimpleManager {
    public TravelTableSimpleManager(CqlSession session) {
        super(session);
    }

    public void createTable() {
        executeSimpleStatement(
                "CREATE TABLE travel(\n" +
                        "id int PRIMARY KEY,\n" +
                        "attractions list<text>,\n" +
                        "numberOfSeats int,\n" +
                        "cost int\n" +
                        ");"
        );
    }

    public void insertIntoTable(int id, List<String> attractionsList, int numberOfSeats, int cost) {
        String separator = ", ";
        String attractions = "[";
        for (String tmp : attractionsList) {
            attractions += "'" + tmp + "',";
        }
        attractions = attractions.substring(0, attractions.length() - 1);
        attractions += "]";
        executeSimpleStatement("INSERT INTO travel(id, attractions, numberOfSeats, cost) " +
                "VALUES (" + id + separator + attractions + separator + numberOfSeats + separator + cost + ");");
    }

    public void updateCostWhereId(int newCost, int id) {
        executeSimpleStatement("UPDATE travel SET cost = " + newCost + " WHERE id = " + id + ";");
    }

    public void deleteFromTable(int id) {
        executeSimpleStatement("DELETE FROM travel WHERE id = " + id + ";");
    }

    public void selectFromTable(int id) {
        String statement = "SELECT * FROM travel;";
        ResultSet resultSet = session.execute(statement);
        for (Row row : resultSet) {
            if (row.getInt("id") != id)
                continue;
            System.out.print("podroz: ");
            System.out.print("ID: " + row.getInt("id") + ", ");
            System.out.print("Lista atrakcji: " + row.getList("attractions", String.class) + ", ");
            System.out.print("Ilosc miejsc: " + row.getInt("numberOfSeats") + ", ");
            System.out.println("Koszt: " + row.getInt("cost") + "\n");
        }
    }

    public void selectAllFromTable() {
        String statement = "SELECT * FROM travel;";
        ResultSet resultSet = session.execute(statement);
        System.out.println(statement + ":\n");
        for (Row row : resultSet) {
            System.out.print("podroz: ");
            System.out.print("ID: " + row.getInt("id") + ", ");
            System.out.print("Lista atrakcji: " + row.getList("attractions", String.class) + ", ");
            System.out.print("Ilosc miejsc: " + row.getInt("numberOfSeats") + ", ");
            System.out.println("Koszt: " + row.getInt("cost") + "\n");
        }
    }

    public void dropTable() {
        executeSimpleStatement("DROP TABLE travel;");
    }

    public void makeTravelsCostHigher(){
        String statement = "SELECT * FROM travel;";
        ResultSet resultSet = session.execute(statement);
        for (Row row : resultSet)
            executeSimpleStatement("UPDATE travel SET cost = " + row.getInt("cost")+100 + " WHERE id = " + row.getInt("id") + ";");
    }
}
