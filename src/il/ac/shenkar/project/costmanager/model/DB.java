/**
 * The DB interface is used to connect and to correspond with the actual DB.
 * This interface will be implemented using a specific DB (such as Derby, MongoDB, MySQL, etc.)
 */

package il.ac.shenkar.project.costmanager.model;
import java.sql.ResultSet;

public interface DB {
    /*
     * openConnection method creates the DB and initializes the connection.
     */
    public abstract void openConnection() throws CostManagerException;
    /*
     * closeConnection method closes the connection safely.
     */
    public abstract void closeConnection() throws CostManagerException;

    /*
     * createCategoryTable method creates the Category table.
     */
    public abstract void createCategoryTable() throws CostManagerException;
    /*
     * createExpenseTable method creates the Expense table.
     */
    public abstract void createExpenseTable() throws CostManagerException;
    /*
     * createIncomeTable method creates the Income table.
     */
    public abstract void createIncomeTable() throws CostManagerException;

    /*
     * The get method executes the query it receives as a parameter
     * and returns the corresponding result set.
     */
    public abstract ResultSet get(String query) throws CostManagerException;
    /*
     * The set method executes the query it receives as a parameter
     *  and returns an indication if the action was successful or not.
     */
    public abstract boolean set(String query) throws CostManagerException;
}
