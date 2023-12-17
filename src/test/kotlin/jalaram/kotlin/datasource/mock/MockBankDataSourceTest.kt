package jalaram.kotlin.datasource.mock


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private val mockBankDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun should  (){
        //when
        val bank = mockBankDataSource.retrieveBank()
        
        //then
        assertThat(bank).isNotEmpty
        assertThat(bank.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock task` (){
        //given
        val bank = mockBankDataSource.retrieveBank()
        
        //when
        assertThat(bank).allMatch{ it.accountNumber.isNotBlank() }
        assertThat(bank).anyMatch{ it.trust != 0.0}
        assertThat(bank).anyMatch{ it.transactionFee != 0 }
    }

}