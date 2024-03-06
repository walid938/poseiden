package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BidListServiceImplTest {

    BidList bidList = new BidList();

    @Autowired
    private BidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeAll
    public void initBidList() {
        bidList.setAccount("account");
        bidList.setType("type");
        bidList.setBidQuantity(10.0);
        bidListService.insertBidList(bidList);

    }

    @AfterAll
    public void cleanBidList() {
        bidListRepository.deleteAll();
    }

    @Test
    public void test1_FindAllBidList() {
        List<BidList> lists = bidListService.findAll();
        assertFalse(lists.isEmpty());
    }

    @Test
    public void testGetBidListById() {
        Integer bidListId = bidList.getBidListId();
        BidList foundBid = bidListService.findById(bidListId);
        assertEquals("account", bidList.getAccount());
        assertEquals("type", bidList.getType());
        assertEquals(10.0, bidList.getBidQuantity(), 0.01);
    }



    @Test
    public void test4_DeleteBidListById() {
        Integer bidListId = bidList.getBidListId();
        bidListService.deleteById(bidListId);
        BidList byId = bidListService.findById(bidListId);
        assertNull(byId);
    }


}