package com.gadies.suzuki.data.repository;

import android.content.Context;
import com.google.gson.Gson;
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
public final class PidRepository_Factory implements Factory<PidRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<Gson> gsonProvider;

  public PidRepository_Factory(Provider<Context> contextProvider, Provider<Gson> gsonProvider) {
    this.contextProvider = contextProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public PidRepository get() {
    return newInstance(contextProvider.get(), gsonProvider.get());
  }

  public static PidRepository_Factory create(Provider<Context> contextProvider,
      Provider<Gson> gsonProvider) {
    return new PidRepository_Factory(contextProvider, gsonProvider);
  }

  public static PidRepository newInstance(Context context, Gson gson) {
    return new PidRepository(context, gson);
  }
}
