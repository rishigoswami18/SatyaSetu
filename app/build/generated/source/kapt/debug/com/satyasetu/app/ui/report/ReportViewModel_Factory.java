package com.satyasetu.app.ui.report;

import com.satyasetu.app.data.repository.AuthRepository;
import com.satyasetu.app.data.repository.ReportRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ReportViewModel_Factory implements Factory<ReportViewModel> {
  private final Provider<ReportRepository> reportRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ReportViewModel_Factory(Provider<ReportRepository> reportRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.reportRepositoryProvider = reportRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ReportViewModel get() {
    return newInstance(reportRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ReportViewModel_Factory create(Provider<ReportRepository> reportRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ReportViewModel_Factory(reportRepositoryProvider, authRepositoryProvider);
  }

  public static ReportViewModel newInstance(ReportRepository reportRepository,
      AuthRepository authRepository) {
    return new ReportViewModel(reportRepository, authRepository);
  }
}
