package project;

import java.sql.Connection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import static project.Transaction.*;

public class TransactionThread extends Thread {

    private final int count;
    private final CountDownLatch latch1;
    private final CountDownLatch latch2;
    private final CountDownLatch latch3;
    private final Connection connection;
    private final TransactionsEnum currentTransaction;
    private String transactionResultInsert;
    private String transactionResultUpdate;
    private String transactionResultSelect;

    public enum TransactionsEnum {
        SELECT, INSERT, UPDATE
    }

    public TransactionThread(int count,
                             TransactionsEnum currentTransaction,
                             CountDownLatch latch1,
                             CountDownLatch latch2,
                             CountDownLatch latch3,
                             Connection connection) {
        this.count = count;
        this.currentTransaction = currentTransaction;
        this.latch1 = latch1;
        this.latch2 = latch2;
        this.latch3 = latch3;
        this.connection = connection;
    }

    public void run() {
        latch1.countDown();
        try {
            latch2.await();
            switch (currentTransaction) {
                case INSERT:
                    transactionResultInsert = insert(count, connection);
                    break;
                case SELECT:
                    transactionResultSelect = select(count, connection);
                    break;
                case UPDATE:
                    transactionResultUpdate = update(count, connection);
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch3.countDown();
        }
    }


    public String getTransactionResultInsert() {
        return transactionResultInsert;
    }

    public void setTransactionResultInsert(String transactionResultInsert) {
        this.transactionResultInsert = transactionResultInsert;
    }

    public String getTransactionResultUpdate() {
        return transactionResultUpdate;
    }

    public void setTransactionResultUpdate(String transactionResultUpdate) {
        this.transactionResultUpdate = transactionResultUpdate;
    }

    public String getTransactionResultSelect() {
        return transactionResultSelect;
    }

    public void setTransactionResultSelect(String transactionResultSelect) {
        this.transactionResultSelect = transactionResultSelect;
    }
}

