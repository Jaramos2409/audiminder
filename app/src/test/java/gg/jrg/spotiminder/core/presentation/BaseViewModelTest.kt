package gg.jrg.spotiminder.core.presentation

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class BaseViewModelTest {

    private lateinit var viewModel: TestViewModel

    @Before
    fun setup() {
        viewModel = TestViewModel()
    }

    @Test
    fun `sending NavEvent_To changes value of navEvent`() = runTest {
        val expectedDirections = object : NavDirections {
            override val actionId: Int
                get() = 0
            override val arguments: Bundle
                get() = Bundle()
        }

        viewModel.navigate(BaseViewModel.NavEvent.To(expectedDirections))

        assertThat(viewModel.navEvent.value, `is`(BaseViewModel.NavEvent.To(expectedDirections)))
    }

    @Test
    fun `sending NavEvent_Up changes value of navEvent`() = runTest {
        viewModel.navigate(BaseViewModel.NavEvent.Up)

        assertThat(viewModel.navEvent.value, `is`(BaseViewModel.NavEvent.Up))
    }

    @Test
    fun `sending NavEvent_Back changes value of navEvent`() = runTest {
        viewModel.navigate(BaseViewModel.NavEvent.Back)

        assertThat(viewModel.navEvent.value, `is`(BaseViewModel.NavEvent.Back))
    }

    @Test
    fun `sending NavEvent_BackToRoot changes value of navEvent`() = runTest {
        val expectedDestinationId = 0
        viewModel.navigate(BaseViewModel.NavEvent.BackTo(expectedDestinationId))

        assertThat(
            viewModel.navEvent.value,
            `is`(BaseViewModel.NavEvent.BackTo(expectedDestinationId))
        )
    }

    @Test
    fun `initial value of NavEvent should be null`() = runTest {
        assertThat(viewModel.navEvent.value, nullValue())
    }

}

class TestViewModel : BaseViewModel()

