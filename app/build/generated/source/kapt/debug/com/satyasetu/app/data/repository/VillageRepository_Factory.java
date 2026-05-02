package com.satyasetu.app.data.repository;

import android.content.Context;
import com.google.firebase.database.FirebaseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class VillageRepository_Factory implements Factory<VillageRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  private final Provider<Context> contextProvider;

  public VillageRepository_Factory(Provider<FirebaseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    this.databaseProvider = databaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public VillageRepository get() {
    return newInstance(databaseProvider.get(), contextProvider.get());
  }

  public static VillageRepository_Factory create(Provider<FirebaseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    return new VillageRepository_Factory(databaseProvider, contextProvider);
  }

  public static VillageRepository newInstance(FirebaseDatabase database, Context context) {
    return new VillageRepository(database, context);
  }
}
