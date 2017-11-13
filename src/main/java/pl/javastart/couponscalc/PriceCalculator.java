package pl.javastart.couponscalc;

import java.util.*;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {

        Map<Category, Double> prizePerCategory;
        double maximalAmountDiscountFromCategories;
        double maximalAmountDiscountedWithoutCategory = 0;
        double maximalAmountDiscount;
        double totalPrizeOfAllProducts;


        if (products == null) {
            return 0;
        }

        totalPrizeOfAllProducts = getTotalPrizeOfAllProducts(products);


        if (((coupons == null) || coupons.size() == 0)) {
            return totalPrizeOfAllProducts;
        }

        prizePerCategory = getPrizePerCategoryMap(products);

        maximalAmountDiscountFromCategories = getAmountOfBiggestDiscountWithinCategory(prizePerCategory, coupons);
        if (couponWithNoCategoryExist(coupons)) {

            maximalAmountDiscountedWithoutCategory = totalPrizeOfAllProducts * discountValueInPercentWithNoCategory(coupons)/100;
        }

        maximalAmountDiscount = Math.max(maximalAmountDiscountedWithoutCategory, maximalAmountDiscountFromCategories);


        return totalPrizeOfAllProducts - maximalAmountDiscount;
    }

    private double discountValueInPercentWithNoCategory(List<Coupon> coupons) {
    double maxDiscountValue = 0;
        for (Coupon coupon :
                coupons) {
            if (coupon.getCategory() == null) {
                if (maxDiscountValue< coupon.getDiscountValueInPercents()){
                    maxDiscountValue = coupon.getDiscountValueInPercents();
                }
            }
        }
        return maxDiscountValue;
    }


    private double getAmountOfNoCategoryDiscount(Map<Category, Double> prizePerCategory) {


        return 0.0;
    }

    private Double getAmountOfBiggestDiscountWithinCategory(Map<Category, Double> prizePerCategory, List<Coupon> coupons) {

        double biggestAmountOfMoneyDiscounted = 0;
        double amountOfMoneyDiscountedForGivenCategory = 0;
        Integer greatestDiscountFromCategory;
        Category categoryOfMaxDiscount = null;
        Map<Category, Double> biggestAmountDiscounted = null;

        for (Category category : prizePerCategory.keySet()) {
            greatestDiscountFromCategory = getCouponWithBiggestDiscountForGivenCategory(coupons, category);

            amountOfMoneyDiscountedForGivenCategory = prizePerCategory.get(category)
                    * greatestDiscountFromCategory / 100;   //discount is given in percentage
            if (amountOfMoneyDiscountedForGivenCategory > biggestAmountOfMoneyDiscounted) {
                biggestAmountOfMoneyDiscounted = amountOfMoneyDiscountedForGivenCategory;
                categoryOfMaxDiscount = category;
            }

        }

        return biggestAmountOfMoneyDiscounted;
    }

    private Integer getCouponWithBiggestDiscountForGivenCategory(List<Coupon> coupons, Category category) {
        int discountFromCoupon;
        int maxDiscount = 0;
        Category categoryFromCoupon;

        for (Coupon coupon : coupons) {
            discountFromCoupon = coupon.getDiscountValueInPercents();
            categoryFromCoupon = coupon.getCategory();
            //coupon with no categories
            if (categoryFromCoupon == null) {
                categoryFromCoupon = Category.OTHER;
            }
            if (categoryFromCoupon.equals(category)) {
                if (maxDiscount < discountFromCoupon) {
                    maxDiscount = discountFromCoupon;
                }
            }
        }
        return  maxDiscount ;
    }

    private Map<Category, Double> getPrizePerCategoryMap(List<Product> products) {

        Map<Category, Double> prizePerCategory = new HashMap<>();
        for (Product product : products) {
            Double prize;
            Category category = product.getCategory();
            if (prizePerCategory.containsKey(category)) {
                prize = prizePerCategory.get(category) + product.getPrice();
                prizePerCategory.put(category, prize);
            } else {
                prizePerCategory.put(category, product.getPrice());
            }


        }


        return prizePerCategory;
    }

    private boolean couponWithNoCategoryExist(List<Coupon> coupons) {

        for (Coupon coupon :
                coupons) {
            if (coupon.getCategory() == null) {
                return true;
            }
        }
        return false;
    }

    private double getTotalPrizeOfAllProducts(List<Product> products) {
        double sum = 0;

        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }
}
