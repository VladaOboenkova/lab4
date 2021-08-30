package project;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import static java.sql.Connection.*;
import static project.TransactionThread.TransactionsEnum.*;
import static project.WriteData.writeData;

public class TransactionStart {

    public static void transactionStart(int count, int isolationLevel) {

        CountDownLatch latch1 = new CountDownLatch(3);
        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(3);

        Connection insertConnection = DBConnection.getConnection(isolationLevel);
        Connection selectConnection = DBConnection.getConnection(isolationLevel);
        Connection updateConnection = DBConnection.getConnection(isolationLevel);

        TransactionThread insertThread = new TransactionThread(count, INSERT, latch1, latch2, latch3, insertConnection);
        TransactionThread selectThread = new TransactionThread(count, SELECT, latch1, latch2, latch3, selectConnection);
        TransactionThread updateThread = new TransactionThread(count, UPDATE, latch1, latch2, latch3, updateConnection);

        insertThread.start();
        selectThread.start();
        updateThread.start();

        try {
            latch1.await();
            latch2.countDown();
            latch3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String resultInsert = insertThread.getTransactionResultInsert();
        String resultSelect = selectThread.getTransactionResultSelect();
        String resultUpdate = updateThread.getTransactionResultUpdate();


        switch (isolationLevel) {
            case TRANSACTION_READ_COMMITTED : fixData(1, resultInsert, resultSelect, resultUpdate);
                break;
            case TRANSACTION_REPEATABLE_READ : fixData(2, resultInsert, resultSelect, resultUpdate);
                break;
            case TRANSACTION_SERIALIZABLE : fixData(3, resultInsert, resultSelect, resultUpdate);
                break;        }

    }

    private static void fixData(
            int level, String resultInsert, String resultSelect, String resultUpdate) {
        if (level == 1) {
            writeData(resultInsert, "readCommitted");
            writeData(resultSelect, "readCommitted");
            writeData(resultUpdate, "readCommitted");
        } else if (level == 2) {
            writeData(resultInsert, "repeatableRead");
            writeData(resultSelect, "repeatableRead");
            writeData(resultUpdate, "repeatableRead");
        } else if (level == 3) {
            writeData(resultInsert, "serializable");
            writeData(resultSelect, "serializable");
            writeData(resultUpdate, "serializable");
        }
    }

}

