package com.hrizzon.demo2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrizzon.demo2.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class Demo2ApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAllEtiquettes_shouldBe200ok() throws Exception {

        mvc.perform(get("/etiquettes"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = {"CLIENT"})
    void getAllProductAsClient_shouldBe200ok() throws Exception {

        mvc.perform(get("/products"))
                .andExpect(status().isOk());

    }


    @Test
    void getClientwithId2_shouldIncludeOnlyNeededInformation() throws Exception {

        mvc.perform(get("/client/2"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.numero").exists())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist());

    }

    @Test
    @WithUserDetails("d@d.com")
        // obligatoire si le contrôleur utilise @AuthenticationPrincipal
    void deleteAsVendeurButNotOwner_shouldBe403forbidden() throws Exception {

        mvc.perform(delete("/product/1"))
                .andExpect(status().isForbidden());

    }


    @Test
    @WithUserDetails("a@a.com")
        // obligatoire si le contrôleur utilise @AuthenticationPrincipal
    void deleteAsVendeurOwner_shouldBe204NoContent() throws Exception {

        mvc.perform(delete("/product/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails("c@c.com")
        // obligatoire si le contrôleur utilise @AuthenticationPrincipal
    void deleteAsChiefSellerButNotOwner_shouldBe204NoContent() throws Exception {

        mvc.perform(delete("/product/6"))
                .andExpect(status().isNoContent());

    }

    @Test
    @WithUserDetails("a@a.com")
    void addNewProductWithMandatoryFields_shouldBe201created() throws Exception {

        Product product = new Product();
        product.setNom("test");
        product.setCodeArticle("test");
        product.setPrix(0.11f);
        String jsonProduct = mapper.writeValueAsString(product);

        mvc.perform(
                        post("/product")
                                .contentType("application/json")
                                .content(jsonProduct)
                )
                .andExpect(status().isCreated());

    }

}