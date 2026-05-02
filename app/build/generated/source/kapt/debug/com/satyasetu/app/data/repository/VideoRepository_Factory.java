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
public final class VideoRepository_Factory implements Factory<VideoRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  public VideoRepository_Factory(Provider<FirebaseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VideoRepository get() {
    return newInstance(databaseProvider.get());
  }

  public static VideoRepository_Factory create(Provider<FirebaseDatabase> databaseProvider) {
    return new VideoRepository_Factory(databaseProvider);
  }

  public static VideoRepository newInstance(FirebaseDatabase database) {
    return new VideoRepository(database);
  }
}
