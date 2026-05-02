package com.satyasetu.app.data.repository;

import com.google.firebase.database.FirebaseDatabase;
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
public final class MythRepository_Factory implements Factory<MythRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  public MythRepository_Factory(Provider<FirebaseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MythRepository get() {
    return newInstance(databaseProvider.get());
  }

  public static MythRepository_Factory create(Provider<FirebaseDatabase> databaseProvider) {
    return new MythRepository_Factory(databaseProvider);
  }

  public static MythRepository newInstance(FirebaseDatabase database) {
    return new MythRepository(database);
  }
}
