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
public final class LearningRepository_Factory implements Factory<LearningRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  public LearningRepository_Factory(Provider<FirebaseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public LearningRepository get() {
    return newInstance(databaseProvider.get());
  }

  public static LearningRepository_Factory create(Provider<FirebaseDatabase> databaseProvider) {
    return new LearningRepository_Factory(databaseProvider);
  }

  public static LearningRepository newInstance(FirebaseDatabase database) {
    return new LearningRepository(database);
  }
}
