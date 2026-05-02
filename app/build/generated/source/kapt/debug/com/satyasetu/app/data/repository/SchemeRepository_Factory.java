package com.satyasetu.app.data.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SchemeRepository_Factory implements Factory<SchemeRepository> {
  @Override
  public SchemeRepository get() {
    return newInstance();
  }

  public static SchemeRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SchemeRepository newInstance() {
    return new SchemeRepository();
  }

  private static final class InstanceHolder {
    private static final SchemeRepository_Factory INSTANCE = new SchemeRepository_Factory();
  }
}
