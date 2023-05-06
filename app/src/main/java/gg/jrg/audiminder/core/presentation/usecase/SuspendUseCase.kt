package gg.jrg.audiminder.core.presentation.usecase

/**
 * SuspendUseCase is a generic interface that represents a use case.
 * This is the suspend version of UseCase.
 * @param Input The input type of the use case
 * @param Output The output type of the use case
 */
interface SuspendUseCase<in Input, out Output> {
    @Suppress("UNCHECKED_CAST")
    suspend operator fun invoke(input: Input = NoInput as Input): Output
}

/**
 * UseCase is a generic interface that represents a use case.
 *
 * @param Input The input type of the use case
 * @param Output The output type of the use case
 */
interface UseCase<in Input, out Output> {
    @Suppress("UNCHECKED_CAST")
    operator fun invoke(input: Input = NoInput as Input): Output
}

object NoInput
