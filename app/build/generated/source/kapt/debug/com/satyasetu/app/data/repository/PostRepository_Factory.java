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
public final class PostRepository_Factory implements Factory<PostRepository> {
  private final Provider<FirebaseDatabase> databaseProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public PostRepository_Factory(Provider<FirebaseDatabase> databaseProvider,
      Provider<FirebaseStorage> storageProvider) {
    this.databaseProvider = databaseProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public PostRepository get() {
    return newInstance(databaseProvider.get(), storageProvider.get());
  }

  public static PostRepository_Factory create(Provider<FirebaseDatabase> databaseProvider,
      Provider<FirebaseStorage> storageProvider) {
    return new PostRepository_Factory(databaseProvider, storageProvider);
  }

  public static PostRepository newInstance(FirebaseDatabase database, FirebaseStorage storage) {
    return new PostRepository(database, storage);
  }
}
