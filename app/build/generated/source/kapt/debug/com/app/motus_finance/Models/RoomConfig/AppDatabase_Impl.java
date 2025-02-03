package com.app.motus_finance.Models.RoomConfig;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.app.motus_finance.Models.DAO.BankDao;
import com.app.motus_finance.Models.DAO.BankDao_Impl;
import com.app.motus_finance.Models.DAO.DueDatesDAO;
import com.app.motus_finance.Models.DAO.DueDatesDAO_Impl;
import com.app.motus_finance.Models.DAO.ExpensesDAO;
import com.app.motus_finance.Models.DAO.ExpensesDAO_Impl;
import com.app.motus_finance.Models.DAO.GraphicsDAO;
import com.app.motus_finance.Models.DAO.GraphicsDAO_Impl;
import com.app.motus_finance.Models.DAO.PaymentDAO;
import com.app.motus_finance.Models.DAO.PaymentDAO_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile BankDao _bankDao;

  private volatile ExpensesDAO _expensesDAO;

  private volatile PaymentDAO _paymentDAO;

  private volatile DueDatesDAO _dueDatesDAO;

  private volatile GraphicsDAO _graphicsDAO;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `bank_entity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `color` TEXT, `img` INTEGER, `balance` REAL, `colorSpentsOrReceived` TEXT, `date` TEXT, `sum` REAL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `table_expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bankId` INTEGER, `expenseDescription` TEXT, `value` REAL, `spentOrReceived` TEXT, `fixedOrVariable` TEXT, `date` TEXT, `dueDate` TEXT, `classification` TEXT, `readyForDeletion` INTEGER NOT NULL, FOREIGN KEY(`bankId`) REFERENCES `bank_entity`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE TABLE IF NOT EXISTS `payment_table` (`id` INTEGER NOT NULL, `payment` REAL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dueDate_entity` (`id` INTEGER NOT NULL, `dueDate` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `graphics_entity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `monthly` TEXT, `value` REAL, `highestSpendingRating` TEXT NOT NULL, `valueSpendingRating` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '04dc8c47db27c73bd14b882bcfb8a610')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `bank_entity`");
        db.execSQL("DROP TABLE IF EXISTS `table_expenses`");
        db.execSQL("DROP TABLE IF EXISTS `payment_table`");
        db.execSQL("DROP TABLE IF EXISTS `dueDate_entity`");
        db.execSQL("DROP TABLE IF EXISTS `graphics_entity`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBankEntity = new HashMap<String, TableInfo.Column>(8);
        _columnsBankEntity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("color", new TableInfo.Column("color", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("img", new TableInfo.Column("img", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("balance", new TableInfo.Column("balance", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("colorSpentsOrReceived", new TableInfo.Column("colorSpentsOrReceived", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBankEntity.put("sum", new TableInfo.Column("sum", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBankEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBankEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBankEntity = new TableInfo("bank_entity", _columnsBankEntity, _foreignKeysBankEntity, _indicesBankEntity);
        final TableInfo _existingBankEntity = TableInfo.read(db, "bank_entity");
        if (!_infoBankEntity.equals(_existingBankEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "bank_entity(com.app.motus_finance.Models.Entities.Banks).\n"
                  + " Expected:\n" + _infoBankEntity + "\n"
                  + " Found:\n" + _existingBankEntity);
        }
        final HashMap<String, TableInfo.Column> _columnsTableExpenses = new HashMap<String, TableInfo.Column>(10);
        _columnsTableExpenses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("bankId", new TableInfo.Column("bankId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("expenseDescription", new TableInfo.Column("expenseDescription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("value", new TableInfo.Column("value", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("spentOrReceived", new TableInfo.Column("spentOrReceived", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("fixedOrVariable", new TableInfo.Column("fixedOrVariable", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("dueDate", new TableInfo.Column("dueDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("classification", new TableInfo.Column("classification", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableExpenses.put("readyForDeletion", new TableInfo.Column("readyForDeletion", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTableExpenses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTableExpenses.add(new TableInfo.ForeignKey("bank_entity", "CASCADE", "NO ACTION", Arrays.asList("bankId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTableExpenses = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTableExpenses = new TableInfo("table_expenses", _columnsTableExpenses, _foreignKeysTableExpenses, _indicesTableExpenses);
        final TableInfo _existingTableExpenses = TableInfo.read(db, "table_expenses");
        if (!_infoTableExpenses.equals(_existingTableExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "table_expenses(com.app.motus_finance.Models.Entities.Expenses).\n"
                  + " Expected:\n" + _infoTableExpenses + "\n"
                  + " Found:\n" + _existingTableExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsPaymentTable = new HashMap<String, TableInfo.Column>(2);
        _columnsPaymentTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPaymentTable.put("payment", new TableInfo.Column("payment", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPaymentTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPaymentTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPaymentTable = new TableInfo("payment_table", _columnsPaymentTable, _foreignKeysPaymentTable, _indicesPaymentTable);
        final TableInfo _existingPaymentTable = TableInfo.read(db, "payment_table");
        if (!_infoPaymentTable.equals(_existingPaymentTable)) {
          return new RoomOpenHelper.ValidationResult(false, "payment_table(com.app.motus_finance.Models.Entities.Payments).\n"
                  + " Expected:\n" + _infoPaymentTable + "\n"
                  + " Found:\n" + _existingPaymentTable);
        }
        final HashMap<String, TableInfo.Column> _columnsDueDateEntity = new HashMap<String, TableInfo.Column>(2);
        _columnsDueDateEntity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDueDateEntity.put("dueDate", new TableInfo.Column("dueDate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDueDateEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDueDateEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDueDateEntity = new TableInfo("dueDate_entity", _columnsDueDateEntity, _foreignKeysDueDateEntity, _indicesDueDateEntity);
        final TableInfo _existingDueDateEntity = TableInfo.read(db, "dueDate_entity");
        if (!_infoDueDateEntity.equals(_existingDueDateEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "dueDate_entity(com.app.motus_finance.Models.Entities.DueDates).\n"
                  + " Expected:\n" + _infoDueDateEntity + "\n"
                  + " Found:\n" + _existingDueDateEntity);
        }
        final HashMap<String, TableInfo.Column> _columnsGraphicsEntity = new HashMap<String, TableInfo.Column>(5);
        _columnsGraphicsEntity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGraphicsEntity.put("monthly", new TableInfo.Column("monthly", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGraphicsEntity.put("value", new TableInfo.Column("value", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGraphicsEntity.put("highestSpendingRating", new TableInfo.Column("highestSpendingRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGraphicsEntity.put("valueSpendingRating", new TableInfo.Column("valueSpendingRating", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGraphicsEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGraphicsEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGraphicsEntity = new TableInfo("graphics_entity", _columnsGraphicsEntity, _foreignKeysGraphicsEntity, _indicesGraphicsEntity);
        final TableInfo _existingGraphicsEntity = TableInfo.read(db, "graphics_entity");
        if (!_infoGraphicsEntity.equals(_existingGraphicsEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "graphics_entity(com.app.motus_finance.Models.Entities.Graphics).\n"
                  + " Expected:\n" + _infoGraphicsEntity + "\n"
                  + " Found:\n" + _existingGraphicsEntity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "04dc8c47db27c73bd14b882bcfb8a610", "bb132d18d025d6b1d5b0139f5ebd5d18");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "bank_entity","table_expenses","payment_table","dueDate_entity","graphics_entity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `bank_entity`");
      _db.execSQL("DELETE FROM `table_expenses`");
      _db.execSQL("DELETE FROM `payment_table`");
      _db.execSQL("DELETE FROM `dueDate_entity`");
      _db.execSQL("DELETE FROM `graphics_entity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BankDao.class, BankDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpensesDAO.class, ExpensesDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(PaymentDAO.class, PaymentDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(DueDatesDAO.class, DueDatesDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(GraphicsDAO.class, GraphicsDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BankDao bankDao() {
    if (_bankDao != null) {
      return _bankDao;
    } else {
      synchronized(this) {
        if(_bankDao == null) {
          _bankDao = new BankDao_Impl(this);
        }
        return _bankDao;
      }
    }
  }

  @Override
  public ExpensesDAO expenseDao() {
    if (_expensesDAO != null) {
      return _expensesDAO;
    } else {
      synchronized(this) {
        if(_expensesDAO == null) {
          _expensesDAO = new ExpensesDAO_Impl(this);
        }
        return _expensesDAO;
      }
    }
  }

  @Override
  public PaymentDAO paymentsDao() {
    if (_paymentDAO != null) {
      return _paymentDAO;
    } else {
      synchronized(this) {
        if(_paymentDAO == null) {
          _paymentDAO = new PaymentDAO_Impl(this);
        }
        return _paymentDAO;
      }
    }
  }

  @Override
  public DueDatesDAO dueDateDao() {
    if (_dueDatesDAO != null) {
      return _dueDatesDAO;
    } else {
      synchronized(this) {
        if(_dueDatesDAO == null) {
          _dueDatesDAO = new DueDatesDAO_Impl(this);
        }
        return _dueDatesDAO;
      }
    }
  }

  @Override
  public GraphicsDAO graphicsDao() {
    if (_graphicsDAO != null) {
      return _graphicsDAO;
    } else {
      synchronized(this) {
        if(_graphicsDAO == null) {
          _graphicsDAO = new GraphicsDAO_Impl(this);
        }
        return _graphicsDAO;
      }
    }
  }
}
