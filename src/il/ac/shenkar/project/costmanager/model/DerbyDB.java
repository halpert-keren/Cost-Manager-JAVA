/**
 * The DerbyDB class is used to connect and to correspond with the actual DB.
 * This class is implemented using a the syntax of Derby specific language.
 * @param connection is an object of type Connection to be used to establish the connection the the DB
 * @param statement is an object of type Statement to be used for executing requests to the DB
 * @param rs is an object of type ResultSet ti be used for the data returned by the DB from a query
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.*;


public class DerbyDB implements DB {
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    /*
     * The constructor calls the openConnection method so that the connection may be established.
     */
    public DerbyDB() throws CostManagerException {
        this.openConnection();

        this.createCategoryTable();
        this.createExpenseTable();
        this.createIncomeTable();

//        this.closeConnection();
    }

    /*
     * openConnection method creates the DB and initializes the connection.
     */
    public void openConnection() throws CostManagerException{
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String protocol = "jdbc:derby:CostManager;create=true";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException ("Problem with class driver", e);
        }
        try {
            connection = DriverManager.getConnection(protocol);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException ("Problem with create DB connection", e);
        }
        try {
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new CostManagerException ("statement failed", e);
        }

        System.out.println("DB is connected!");
    }

    /*
     * closeConnection method closes the connection safely.
     */
    public void closeConnection() throws CostManagerException{
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException se) {
            if ("XJ015".equals(se.getSQLState())) {
            } else {
                se.printStackTrace();
                throw new CostManagerException ("shutdown error", se);
            }
        }
        if (statement != null)
            try {
                statement.close();
            } catch (SQLException se) {
                se.printStackTrace();
                throw new CostManagerException ("statement couldn't close", se);
            } finally {
                statement = null;
            }

        if (connection != null)
            try {
                connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
                throw new CostManagerException ("Connection couldn't close", se);
            } finally {
                connection = null;
            }

        if (rs != null)
            try {
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
                throw new CostManagerException ("rs couldn't close", se);
            } finally {
                rs = null;
            }
    }

    /*
     * createCategoryTable method creates the Category table.
     */
    public void createCategoryTable() throws CostManagerException{
        try {
            statement.execute("create table Category(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(50) UNIQUE)");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
            } else {
                se.printStackTrace();
                throw new CostManagerException ("Problem with creating Category table", se);
            }
        }
    }


    /*
     * createExpenseTable method creates the Expense table.
     */
    public void createExpenseTable() throws CostManagerException{
        try {
            statement.execute("create table Expense(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "description VARCHAR(200), " +
                    "\"sum\" FLOAT, " +
                    "date DATE, " +
                    "category VARCHAR(50), " +
                    "FOREIGN KEY (category) REFERENCES Category(name))");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
            } else {
                se.printStackTrace();
                throw new CostManagerException ("Problem with creating Expense table", se);
            }
        }
    }


    /*
     * createIncomeTable method creates the Income table.
     */
    public void createIncomeTable() throws CostManagerException{
        try {
            statement.execute("create table Income(" +
                    "id INT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "description VARCHAR(200), " +
                    "\"sum\" FLOAT, " +
                    "date DATE)");
        } catch (SQLException se) {
            if ("X0Y32".equals(se.getSQLState())) {
            } else {
                se.printStackTrace();
                throw new CostManagerException ("Problem with creating Income table", se);
            }
        }
    }

    /*
     * The get method executes the query it receives as a parameter
     * and returns the corresponding result set.
     */
    public ResultSet get(String query)throws CostManagerException {
        try {
//            openConnection();
            rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
            se.printStackTrace();
            throw new CostManagerException ("problem with get", se);
        } finally {
//            closeConnection();
        }
    }


    /*
     * The set method executes the query it receives as a parameter
     * and returns an indication if the action was successful or not.
     */
    public boolean set(String query) throws CostManagerException{
        try {
//            openConnection();
            statement.execute(query);
            return true;
        } catch(SQLException se) {
            if ("23505".equals(se.getSQLState())) {
                return true;
            } else {
                se.printStackTrace();
                throw new CostManagerException ("problem with set", se);
            }
        } finally {
//            closeConnection();
        }
    }
}