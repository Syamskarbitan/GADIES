package com.gadies.suzuki.service;

import com.gadies.suzuki.data.repository.PidRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ObdService_MembersInjector implements MembersInjector<ObdService> {
  private final Provider<PidRepository> pidRepositoryProvider;

  public ObdService_MembersInjector(Provider<PidRepository> pidRepositoryProvider) {
    this.pidRepositoryProvider = pidRepositoryProvider;
  }

  public static MembersInjector<ObdService> create(Provider<PidRepository> pidRepositoryProvider) {
    return new ObdService_MembersInjector(pidRepositoryProvider);
  }

  @Override
  public void injectMembers(ObdService instance) {
    injectPidRepository(instance, pidRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.gadies.suzuki.service.ObdService.pidRepository")
  public static void injectPidRepository(ObdService instance, PidRepository pidRepository) {
    instance.pidRepository = pidRepository;
  }
}
