package com.app.motus_finance.Models.DAO;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.motus_finance.Models.Entities.Expenses;
import com.app.motus_finance.Models.Entities.HighestSpending;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ExpensesDAO_Impl implements ExpensesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Expenses> __insertionAdapterOfExpenses;

  private final SharedSQLiteStatement __preparedStmtOfDeleteVariables;

  public ExpensesDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpenses = new EntityInsertionAdapter<Expenses>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `table_expenses` (`id`,`bankId`,`expenseDescription`,`value`,`spentOrReceived`,`fixedOrVariable`,`date`,`dueDate`,`classification`,`readyForDeletion`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Expenses entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getBankId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getBankId());
        }
        if (entity.getExpenseDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getExpenseDescription());
        }
        if (entity.getValue() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getValue());
        }
        if (entity.getSpentOrReceived() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSpentOrReceived());
        }
        if (entity.getFixedOrVariable() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFixedOrVariable());
        }
        if (entity.getDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDate());
        }
        if (entity.getDueDate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDueDate());
        }
        if (entity.getClassification() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getClassification());
        }
        final int _tmp = entity.getReadyForDeletion() ? 1 : 0;
        statement.bindLong(10, _tmp);
      }
    };
    this.__preparedStmtOfDeleteVariables = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM table_expenses WHERE fixedOrVariable = 'Variable' AND bankId= ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertExpenses(final Expenses expenses,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfExpenses.insert(expenses);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteVariables(final int bankId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteVariables.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, bankId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteVariables.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Double getTotalExpenses(final String fixedOrVariable, final int bankId) {
    final String _sql = "SELECT SUM(value) FROM table_expenses WHERE fixedOrVariable = ? AND bankId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (fixedOrVariable == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fixedOrVariable);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, bankId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Object getAllSpendingRatings(
      final Continuation<? super List<HighestSpending>> $completion) {
    final String _sql = "SELECT classification, SUM(value) as total FROM table_expenses GROUP BY classification ORDER BY total DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<HighestSpending>>() {
      @Override
      @NonNull
      public List<HighestSpending> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfClassification = 0;
          final int _cursorIndexOfTotal = 1;
          final List<HighestSpending> _result = new ArrayList<HighestSpending>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighestSpending _item;
            final String _tmpClassification;
            if (_cursor.isNull(_cursorIndexOfClassification)) {
              _tmpClassification = null;
            } else {
              _tmpClassification = _cursor.getString(_cursorIndexOfClassification);
            }
            final double _tmpTotal;
            _tmpTotal = _cursor.getDouble(_cursorIndexOfTotal);
            _item = new HighestSpending(_tmpClassification,_tmpTotal);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
