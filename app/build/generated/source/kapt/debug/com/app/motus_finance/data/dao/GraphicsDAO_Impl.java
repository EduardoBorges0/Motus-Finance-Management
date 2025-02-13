package com.app.motus_finance.data.dao;

import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.app.motus_finance.data.models.Graphics;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class GraphicsDAO_Impl implements GraphicsDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Graphics> __insertionAdapterOfGraphics;

  public GraphicsDAO_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGraphics = new EntityInsertionAdapter<Graphics>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `graphics_entity` (`id`,`monthly`,`value`,`highestSpendingRating`,`valueSpendingRating`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Graphics entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getMonthly() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getMonthly());
        }
        if (entity.getValue() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getValue());
        }
        if (entity.getHighestSpendingRating() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getHighestSpendingRating());
        }
        statement.bindDouble(5, entity.getValueSpendingRating());
      }
    };
  }

  @Override
  public Object insertGraphics(final Graphics graphics,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGraphics.insert(graphics);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
