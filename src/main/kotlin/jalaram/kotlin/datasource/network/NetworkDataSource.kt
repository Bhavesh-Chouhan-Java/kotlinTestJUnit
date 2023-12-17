package jalaram.kotlin.datasource.network

import jalaram.kotlin.datasource.BankDataSource
import jalaram.kotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository("network")
class NetworkDataSource : BankDataSource {
    override fun retrieveBank(): Collection<Bank> {
        TODO("Not yet implemented")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun addBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }

}