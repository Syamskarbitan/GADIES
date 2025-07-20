package com.gadies.suzuki.ui.viewmodel;

import com.gadies.suzuki.data.repository.PidRepository;
import com.gadies.suzuki.service.AiService;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<PidRepository> pidRepositoryProvider;

  private final Provider<AiService> aiServiceProvider;

  public MainViewModel_Factory(Provider<PidRepository> pidRepositoryProvider,
      Provider<AiService> aiServiceProvider) {
    this.pidRepositoryProvider = pidRepositoryProvider;
    this.aiServiceProvider = aiServiceProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(pidRepositoryProvider.get(), aiServiceProvider.get());
  }

  public static MainViewModel_Factory create(Provider<PidRepository> pidRepositoryProvider,
      Provider<AiService> aiServiceProvider) {
    return new MainViewModel_Factory(pidRepositoryProvider, aiServiceProvider);
  }

  public static MainViewModel newInstance(PidRepository pidRepository, AiService aiService) {
    return new MainViewModel(pidRepository, aiService);
  }
}
