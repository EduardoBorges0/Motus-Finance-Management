// Generated by Dagger (https://dagger.dev).
package com.app.motus_finance.presentation.viewmodel;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.internal.lifecycle.HiltViewModelMap.KeySet")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MarketViewModel_HiltModules_KeyModule_ProvideFactory implements Factory<Boolean> {
  @Override
  public Boolean get() {
    return provide();
  }

  public static MarketViewModel_HiltModules_KeyModule_ProvideFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static boolean provide() {
    return MarketViewModel_HiltModules.KeyModule.provide();
  }

  private static final class InstanceHolder {
    private static final MarketViewModel_HiltModules_KeyModule_ProvideFactory INSTANCE = new MarketViewModel_HiltModules_KeyModule_ProvideFactory();
  }
}
