package jalaram.kotlin.controller


import com.fasterxml.jackson.databind.ObjectMapper
import jalaram.kotlin.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper : ObjectMapper
){


    val bankBaseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks` (){
            //given
            mockMvc.get(bankBaseUrl)
                    .andDo { print() }
                    .andExpect {
                        status{
                            isOk()
                        }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].accountNumber"){ value("12345") }
                        jsonPath("$[0].trust"){ value(3.4) }
                        jsonPath("$[0].transactionFee"){ value(4) }
                    }

        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank{
        @Test
        fun `should return the bank from account number` (){
            //given
            val accountNumber = 12345987

            //when
            mockMvc.get(bankBaseUrl.plus("/").plus(accountNumber))
                    .andDo { print() }
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.trust") { value(2.4) }
                        jsonPath("$.transactionFee") { value(4) }
                    }

            //then

        }
        
        
        @Test
        fun `should return NOT FOUND if the account number does not exists` (){
            //given
            val accountNumber = "does_not_exists"
            
            //when
            mockMvc.get(bankBaseUrl.plus("/").plus(accountNumber))
                .andDo {
                    print()
                }
                .andExpect {
                    status { isNotFound() }
                }
            
            
            //then
            
        }
    }
    
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank{

        @Test
        fun `should add the ne bank` (){

            //given
            val newBank = Bank("acc123",31.44,2)
            
            //when
            val performPost = mockMvc.post(bankBaseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            //then
            performPost.andDo { print() }
            .andExpect {
                status { isCreated() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(newBank))
                }
            }

            mockMvc.get(bankBaseUrl.plus("/").plus(newBank.accountNumber)).andExpect {
                content { json(objectMapper.writeValueAsString(newBank)) }
            }

        }
        
        @Test
        fun `should return Bad Request if bank with given account number already exists` (){
            //given
            val invalidBankAccount = Bank("12345",11.0,32)
            
            //when
            val performPost = mockMvc.post(bankBaseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBankAccount)
            }
            
            //then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }

        
    }
    
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank{
        
        @Test
        fun `should update an existing bank` (){
            //given
            val updateBank = Bank("12345",1.0,4)

            
            //when
            val performPatch = mockMvc.patch(bankBaseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateBank)
            }

            //then
            performPatch.andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updateBank))
                    }

                }

            mockMvc.get(bankBaseUrl.plus("/").plus(updateBank.accountNumber))
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updateBank))
                    }
                }
        }

        @Test
        fun `should return bank request if no bank with given account number exists` (){
            //given
            val invalidBank = Bank("does_not_exist",1.0,1)
            
            //when
            val patchRequest = mockMvc.patch(bankBaseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }
            
            
            //then
            patchRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
            
        }
        
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        @DirtiesContext
        fun `should delete by bank account number` (){
            //given
            val accountNumber = 12345

            //when//then
            mockMvc.delete(bankBaseUrl.plus("/").plus(accountNumber))
                .andDo { print() }
                .andExpect { status { isNoContent() } }


            mockMvc.get(bankBaseUrl.plus("/").plus(accountNumber))
                .andExpect { status { isNotFound() } }
        }


        @Test
        fun `should return NOT FOUND id no bank with given account number exists` (){
            //given
            val invalidAccountNumber = "does_not_exists"
            
            //when //then
            mockMvc.delete(bankBaseUrl.plus("/").plus(invalidAccountNumber))
                .andDo { print() }
                .andExpect { status { isNotFound() } }
            

            
        }
    }

}