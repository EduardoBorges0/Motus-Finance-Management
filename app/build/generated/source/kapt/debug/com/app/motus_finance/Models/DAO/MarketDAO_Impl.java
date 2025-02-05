package com.app.motus_finance.Models.DAO;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.motus_finance.Models.Entities.Market;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
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
public final class MarketDAO_Impl implements MarketDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Market> __insertionAdapterOfMarket;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBank;

  private final SharedSQLiteStatement __preparedStmtOfUpdateAllSumToZero;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDatePlusMonth;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBalance;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBankDate;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSum;

  public MarketDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMarket = new EntityInsertionAdapter<Market>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `market_entity` (`id`,`name`,`color`,`img`,`balance`,`colorSpentsOrReceived`,`date`,`sum`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Market entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getColor() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getColor());
        }
        if (entity.getImg() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getImg());
        }
        if (entity.getBalance() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getBalance());
        }
        if (entity.getColorSpentsOrReceived() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getColorSpentsOrReceived());
        }
        if (entity.getDate() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDate());
        }
        if (entity.getSum() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getSum());
        }
      }
    };
    this.__preparedStmtOfDeleteBank = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM market_entity WHERE id= ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateAllSumToZero = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE market_entity SET sum = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDatePlusMonth = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE market_entity SET date = ? WHERE id= ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateBalance = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE market_entity SET balance = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateBankDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE market_entity SET date = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSum = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE market_entity SET sum = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBank(final Market banks, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMarket.insert(banks);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteBank(final int id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBank.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteBank.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateAllSumToZero(final double sum, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateAllSumToZero.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, sum);
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
          __preparedStmtOfUpdateAllSumToZero.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateDatePlusMonth(final String date, final int id,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDatePlusMonth.acquire();
        int _argIndex = 1;
        if (date == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, date);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfUpdateDatePlusMonth.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object updateBalance(final int bankId, final double newBalance,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBalance.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, newBalance);
        _argIndex = 2;
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
          __preparedStmtOfUpdateBalance.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object updateBankDate(final int bankId, final String newDate,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBankDate.acquire();
        int _argIndex = 1;
        if (newDate == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, newDate);
        }
        _argIndex = 2;
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
          __preparedStmtOfUpdateBankDate.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public Object updateSum(final int bankId, final double sum,
      final Continuation<? super Unit> arg2) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSum.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, sum);
        _argIndex = 2;
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
          __preparedStmtOfUpdateSum.release(_stmt);
        }
      }
    }, arg2);
  }

  @Override
  public LiveData<List<Market>> getAllBanks() {
    final String _sql = "SELECT * FROM market_entity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"market_entity"}, false, new Callable<List<Market>>() {
      @Override
      @Nullable
      public List<Market> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfImg = CursorUtil.getColumnIndexOrThrow(_cursor, "img");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfColorSpentsOrReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "colorSpentsOrReceived");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfSum = CursorUtil.getColumnIndexOrThrow(_cursor, "sum");
          final List<Market> _result = new ArrayList<Market>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Market _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpColor;
            if (_cursor.isNull(_cursorIndexOfColor)) {
              _tmpColor = null;
            } else {
              _tmpColor = _cursor.getString(_cursorIndexOfColor);
            }
            final Integer _tmpImg;
            if (_cursor.isNull(_cursorIndexOfImg)) {
              _tmpImg = null;
            } else {
              _tmpImg = _cursor.getInt(_cursorIndexOfImg);
            }
            final Double _tmpBalance;
            if (_cursor.isNull(_cursorIndexOfBalance)) {
              _tmpBalance = null;
            } else {
              _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            }
            final String _tmpColorSpentsOrReceived;
            if (_cursor.isNull(_cursorIndexOfColorSpentsOrReceived)) {
              _tmpColorSpentsOrReceived = null;
            } else {
              _tmpColorSpentsOrReceived = _cursor.getString(_cursorIndexOfColorSpentsOrReceived);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final Double _tmpSum;
            if (_cursor.isNull(_cursorIndexOfSum)) {
              _tmpSum = null;
            } else {
              _tmpSum = _cursor.getDouble(_cursorIndexOfSum);
            }
            _item = new Market(_tmpId,_tmpName,_tmpColor,_tmpImg,_tmpBalance,_tmpColorSpentsOrReceived,_tmpDate,_tmpSum);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<String> getAllDates() {
    final String _sql = "SELECT date FROM market_entity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public String getDatesById(final int id) {
    final String _sql = "SELECT date FROM market_entity WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if (_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getString(0);
        }
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
  public Object getBalanceById(final int id, final Continuation<? super Double> arg1) {
    final String _sql = "SELECT balance FROM market_entity WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getDouble(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object sumAllBank(final Continuation<? super Double> arg0) {
    final String _sql = "SELECT SUM(sum) FROM market_entity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
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
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
