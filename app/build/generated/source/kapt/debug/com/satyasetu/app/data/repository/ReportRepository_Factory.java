package com.satyasetu.app.data.repository;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class ReportRepository_Factory implements Factory<ReportRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public ReportRepository_Factory(Provider<FirebaseDatabase> databaseProvider,
      Provider<FirebaseStorage> storageProvider) {
    this.databaseProvider = databaseProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public ReportRepository get() {
    return newInstance(databaseProvider.get(), storageProvider.get());
  }

  public static ReportRepository_Factory create(Provider<FirebaseDatabase> databaseProvider,
      Provider<FirebaseStorage> storageProvider) {
    return new ReportRepository_Factory(databaseProvider, storageProvider);
  }

  public static ReportRepository newInstance(FirebaseDatabase database, FirebaseStorage storage) {
    return new ReportRepository(database, storage);
  }
}
