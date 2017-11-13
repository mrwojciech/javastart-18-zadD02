package pl.javastart.couponscalc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PriceCalculatorTest {

    @Test
    public void shouldReturnZeroForNoProducts() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();

        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result, is(0.));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result, is(5.99));
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        //rounding to two decimal places
        result = (int)(result*100);
        result = result/100;

        // then
        assertThat(result, is(4.79));
    }




    @Test
    public void shouldSelectBetterDiscountFromTwoCategoriesAvailableDependingOnDiscountAmount() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();

        products.add(new Product("Masło", 6, Category.FOOD));//6 PLN
        products.add(new Product("Opony", 100, Category.CAR)); //100PLN
        coupons.add(new Coupon(Category.FOOD, 80)); // discount: 4.80
        coupons.add(new Coupon(Category.CAR, 5));   //discount 5 PLN

        // when
        double result = priceCalculator.calculatePrice(products, coupons); // 6 + (100 -5) = 101
        //rounding to two decimal places
        result = (int)(result*100);
        result = result/100;

        // then
        assertThat(result, is(101.0));
    }


    @Test
    public void shouldCountNoDiscountWhenNoCouponsAvailable() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();

        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));
        products.add(new Product("Kino", 50, Category.ENTERTAINMENT));
        products.add(new Product("Czajnik", 22, Category.HOME));

        // when
        double result = priceCalculator.calculatePrice(products, coupons); // 6 + 100 + 50 + 22 = 178
        //rounding to two decimal places
        result = (int)(result*100);
        result = result/100;

        // then
        assertThat(result, is(178.0));
    }


    @Test
    public void shouldCountDiscountToAllProductWhenNoCategoryIsGivenToDiscount() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();

        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));
        products.add(new Product("Kino", 50, Category.ENTERTAINMENT));
        products.add(new Product("Czajnik", 22, Category.HOME));
        coupons.add(new Coupon(null, 10));   //discount 5 % for all

        // when
        double result = priceCalculator.calculatePrice(products, coupons); // 6 + 100 + 50 + 22 = 178 - 17.8 10% = 160.20

        // then
        assertThat(result, is(160.2));
    }


    @Test
    public void shouldSelectBetterDiscountIfTwoExistsFroTheSameCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();

        products.add(new Product("Masło", 6, Category.FOOD));
        products.add(new Product("Opony", 100, Category.CAR));
        products.add(new Product("Kino", 50, Category.ENTERTAINMENT));
        products.add(new Product("Czajnik", 22, Category.HOME));
        coupons.add(new Coupon(Category.HOME, 10));   //discount 5 % for all
        coupons.add(new Coupon(Category.HOME, 5));   //discount 5 % for all

        // when
        double result = priceCalculator.calculatePrice(products, coupons); // 6 + 100 + 50 + 22 = 178 - 2.2 =

        // then
        assertThat(result, is(175.8));
    }






}