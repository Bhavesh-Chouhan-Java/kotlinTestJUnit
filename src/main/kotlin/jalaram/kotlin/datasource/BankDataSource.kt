package jalaram.kotlin.datasource

import jalaram.kotlin.model.Bank

interface BankDataSource {
    fun retrieveBank() : Collection<Bank>
    fun retrieveBank(accountNumber:String) : Bank
    fun addBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String)
}