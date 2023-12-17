package jalaram.kotlin.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jalaram.kotlin.datasource.BankDataSource
import org.junit.jupiter.api.Test

internal class BankServiceTest{

    private val dataSource : BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks` (){
        //given

        //when
        bankService.getBank();

        //then
        verify(exactly = 1) { dataSource.retrieveBank() }
    }
}

