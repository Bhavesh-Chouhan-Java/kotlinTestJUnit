package jalaram.kotlin.datasource.mock

import jalaram.kotlin.datasource.BankDataSource
import jalaram.kotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository("mock")
class MockBankDataSource : BankDataSource {



    val list = mutableListOf(
        Bank("12345",3.4,4),
        Bank("12345678",1.4,2),
        Bank("12345987",2.4,4)
    )


    override fun retrieveBank(): Collection<Bank> = list

    override fun retrieveBank(accountNumber: String): Bank = list.firstOrNull { it.accountNumber == accountNumber }
        ?: throw NoSuchElementException("Could not find bank with account number $accountNumber")

    override fun addBank(bank: Bank): Bank {
        if(list.any{ it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }
        list.add(bank)

        return bank;

    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = list.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find with bank account number ${bank.accountNumber}")
        list.remove(currentBank)
        list.add(bank)
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = list.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find with bank account number ${accountNumber}")
        list.remove(currentBank)
    }


}