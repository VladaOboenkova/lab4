package project;

import java.sql.Connection;

import static project.TransactionStart.*;

public class TransactionsApp {

    public static void main(String[] args) {

          transactionStart(1000, Connection.TRANSACTION_READ_COMMITTED);
          transactionStart(1000, Connection.TRANSACTION_REPEATABLE_READ);
          transactionStart(1000, Connection.TRANSACTION_SERIALIZABLE);
    }
}
