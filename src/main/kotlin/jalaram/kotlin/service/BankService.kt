package jalaram.kotlin.service

import jalaram.kotlin.datasource.BankDataSource
import jalaram.kotlin.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("mock") private val dataSource: BankDataSource) {

    fun getBank(): Collection<Bank> =  dataSource.retrieveBank()

    fun getBank(accountNumber: String): Bank =  dataSource.retrieveBank(accountNumber)

    fun addBank(bank : Bank) : Bank = dataSource.addBank(bank)

    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)
}