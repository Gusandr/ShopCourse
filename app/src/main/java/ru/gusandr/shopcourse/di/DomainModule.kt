package ru.gusandr.shopcourse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.gusandr.domain.usecase.OpenSocialAuthUseCase
import ru.gusandr.domain.usecase.ValidateEmailUseCase
import ru.gusandr.domain.usecase.ValidatePasswordUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideOpenSocialAuthUseCase(): OpenSocialAuthUseCase {
        return OpenSocialAuthUseCase()
    }
}