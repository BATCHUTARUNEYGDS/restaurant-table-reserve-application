package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.CreateRestaurantDto;
import com.reserve.restaurantservice.dto.RestaurantDto;
import com.reserve.restaurantservice.entities.*;
import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.exception.NotFoundException;
import com.reserve.restaurantservice.mapper.RestaurantMapper;
import com.reserve.restaurantservice.mapper.RestaurantTableMapper;
import com.reserve.restaurantservice.mapper.RestaurantTypeMapper;
import com.reserve.restaurantservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantTypeRepository restaurantTypeRepository;
    @Autowired
    RestaurantTypeService restaurantTypeService;

    @Autowired
    RestaurantLocationRepository restaurantLocationRepository;

    @Autowired
    RestaurantTableRepository restaurantTableRepository;

    @Autowired
    RestaurantManagerRepository restaurantManagerRepository;

    @Autowired
    RatingCountRepository ratingCountRepository;

    @Autowired
    RestaurantMapper restaurantMapper;

    @Autowired
    RestaurantTableMapper restaurantTableMapper;

    @Autowired
    RestaurantManagerService restaurantManagerService;

    @Autowired
    RestaurantLocationService restaurantLocationService;

    @Autowired
    RestaurantTableService restaurantTableService;

    @Override
    public List<RestaurantDto> getAllRestaurants(Pageable pageable) {
        List<Restaurant> restaurant =  restaurantRepository.findAll(pageable).getContent();
        return restaurantMapper.entityToDtos(restaurant);
        //.stream().collect(Collectors.toList())
    }



    @Override
    public List<Restaurant> getRestaurants(Pageable pageable) {
        List<Restaurant> restaurantsList = restaurantRepository.findAll(pageable).getContent().stream().collect(Collectors.toList());
        return restaurantsList;
    }

    @Override
    public Restaurant getRestaurantById(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            throw new NotFoundException("No restaurant with id :" + restaurantId);
        } else {
            return restaurant;
        }
    }

    @Override
    public Optional<Restaurant> getRestaurantByManager(Integer managerId) {
        RestaurantManager manager = restaurantManagerService.retrieveRestaurantManager(managerId);
        Optional<Restaurant> restaurant = restaurantRepository.findByRestaurantManager(manager);
        if(restaurant != null)
        {
            return restaurant;
        }
        else
        {
            throw new NotFoundException("No restaurant was added to your account until now");
        }
    }

    @Override
    public Restaurant getRestaurantByrestaurantMail(String restaurantMail) {
        Restaurant restaurant = restaurantRepository.findByRestaurantMail(restaurantMail).orElse(null);
        if (restaurant == null) {
            throw new NotFoundException("please check provided email, No restaurant with mail :" + restaurantMail);
        } else {
            return restaurant;
        }
    }


    public Restaurant createRestaurant(Integer pincode, Integer managerId, List<String> restaurantTypes, Restaurant restaurant) {
        Restaurant restaurantMail = restaurantRepository.findByRestaurantMail(restaurant.getRestaurantMail()).orElse(null);
        Restaurant restaurantName = restaurantRepository.findByrestaurantName(restaurant.getRestaurantName()).orElse(null);
        RestaurantLocation restaurantLocation = restaurantLocationRepository.findByPincode(pincode);
        RestaurantManager restaurantManager = restaurantManagerRepository.findById(managerId).orElse(null);
        if (restaurantMail == null && restaurantName == null && restaurantLocation != null && restaurantManager != null) {
            for (int i = 0; i < restaurantTypes.stream().count(); i++) {
                RestaurantType restaurantType10 = restaurantTypeService.getRestaurantTypeByCuisineName(restaurantTypes.get(i));
                restaurant.setCuisineType(restaurantType10);
            }
            restaurant.assignLocation(restaurantLocation);
            restaurant.setRestaurantManager(restaurantManager);
            Restaurant restaurant10 = restaurantRepository.save(restaurant);
            RatingCount ratingCount = new RatingCount();
            ratingCount.setZeroCount(0);
            ratingCount.setOneCount(0);
            ratingCount.setTwoCount(0);
            ratingCount.setThreeCount(0);
            ratingCount.setFourCount(0);
            ratingCount.setFiveCount(0);
            ratingCount.setRestaurant(restaurant);
            RatingCount rc = ratingCountRepository.save(ratingCount);
            return restaurant10;
        } else {
            throw new AlreadyExistException("Restaurant Account already exists");
        }
    }

    public ResponseEntity<String> addRestaurant(CreateRestaurantDto createRestaurantDto)
    {
        Restaurant restaurantMail = restaurantRepository.findByRestaurantMail(createRestaurantDto.getRestaurantMail()).orElse(null);
        Restaurant restaurantName = restaurantRepository.findByrestaurantName(createRestaurantDto.getRestaurantName()).orElse(null);
        RestaurantLocation restaurantLocation = restaurantLocationRepository.findByPincode(createRestaurantDto.getPincode());
        RestaurantManager restaurantManager = restaurantManagerRepository.findById(createRestaurantDto.getRestaurantManagerId()).orElse(null);
        if (restaurantMail == null && restaurantName == null && restaurantLocation != null && restaurantManager != null) {
            Restaurant restaurantEntity = new Restaurant();

            restaurantEntity.setRestaurantName(createRestaurantDto.getRestaurantName());
            restaurantEntity.setRestaurantMail(createRestaurantDto.getRestaurantMail());
            restaurantEntity.setApproxForTwo(createRestaurantDto.getApproxForTwo());
            restaurantEntity.setDescription(createRestaurantDto.getDescription());
            restaurantEntity.setFeatures(createRestaurantDto.getFeatures());
            restaurantEntity.setFullAddress(createRestaurantDto.getFullAddress());
            restaurantEntity.setTag(createRestaurantDto.getTag());
            restaurantEntity.setTimings(createRestaurantDto.getTimings());
            restaurantEntity.assignLocation(restaurantLocation);
            restaurantEntity.setRestaurantContactNumber(createRestaurantDto.getRestaurantContactNumber());
            restaurantEntity.setTableCustomization(createRestaurantDto.getTableCustomization());
            restaurantEntity.setRestaurantManager(restaurantManager);

            Set<String> cuisineNamesSet = createRestaurantDto.getRestaurantCusineType();
            List<String> cuisineNamesList = new ArrayList<>();
            cuisineNamesList.addAll(cuisineNamesSet);
            Set<RestaurantType> restaurantTypeSet = new HashSet<>();
            for (int i = 0; i < cuisineNamesList.size(); i++) {
                RestaurantType restaurantType10 = restaurantTypeService.getRestaurantTypeByCuisineName(cuisineNamesList.get(i));
                restaurantTypeSet.add(restaurantType10);
            }
            restaurantEntity.setRestaurantCusineType(restaurantTypeSet);

            Restaurant restaurant10 = restaurantRepository.save(restaurantEntity);
            for(int i=0;i<createRestaurantDto.getRestaurantTable().size();i++)
            {
                RestaurantTable tableEntity = restaurantTableMapper.dtoToEntity(createRestaurantDto.getRestaurantTable().get(i));
                tableEntity.setTheRestaurant(restaurant10);
                restaurantTableRepository.save(tableEntity);
            }
            RatingCount ratingCount = new RatingCount();
            ratingCount.setZeroCount(0);
            ratingCount.setOneCount(0);
            ratingCount.setTwoCount(0);
            ratingCount.setThreeCount(0);
            ratingCount.setFourCount(0);
            ratingCount.setFiveCount(0);
            ratingCount.setRestaurant(restaurantEntity);
            RatingCount rc = ratingCountRepository.save(ratingCount);
            return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant Account Created");
        } else {
            throw new AlreadyExistException("Restaurant Account already exists");
        }
    }

    @Override
    public ResponseEntity<String> deleteRestaurantById(Integer restaurantId) {
        Restaurant restaurant2 = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant2 != null) {
            restaurantRepository.deleteById(restaurantId);
            return ResponseEntity.ok("Restaurant " + restaurant2.getRestaurantName() + " Account was deleted .");
        } else {
            throw new NotFoundException("No restaurant exist with given id");
        }
    }

    @Override
    public List<Restaurant> getRestaurantByRestaurantTag(String restaurantTag) {
        List<Restaurant> restaurant7 = restaurantRepository.findByTag(restaurantTag);
        if (restaurant7.isEmpty()) {
            throw new NotFoundException("No restaurents exists with tag : " + restaurantTag);
        } else {
            return restaurant7;
        }
    }

    @Override
    public List<Restaurant> getRestaurantByLocation(RestaurantLocation restaurantLocation, Pageable pageable) {
        List<Restaurant> restaurants2 = restaurantRepository.findByRestaurantLocation(restaurantLocation, pageable);
        return restaurants2;
    }

    @Override
    public ResponseEntity<String> updateRestaurant(Integer restaurantId, CreateRestaurantDto NewRestaurant) {
        Restaurant OldRestaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (OldRestaurant != null) {
            if(NewRestaurant.getRestaurantMail()!=null && OldRestaurant.getRestaurantMail()!=NewRestaurant.getRestaurantMail())
            {
                OldRestaurant.setRestaurantMail(NewRestaurant.getRestaurantMail());
            }
            if(NewRestaurant.getRestaurantName()!=null && OldRestaurant.getRestaurantName()!=NewRestaurant.getRestaurantName())
            {
                OldRestaurant.setRestaurantName(NewRestaurant.getRestaurantName());
            }
            if(NewRestaurant.getApproxForTwo()!=null && OldRestaurant.getApproxForTwo()!=NewRestaurant.getApproxForTwo())
            {
                OldRestaurant.setApproxForTwo(NewRestaurant.getApproxForTwo());
            }
            //** when data of a restaurant is updating all cuisine types were unmapped automatically and they should add again freshly
            // if that was not the case below commented logic should be used

//            if(NewRestaurant.getRestaurantCusineType()!=null)
//            {
//
//                Set<RestaurantType> oldRestaurantTypeSet = OldRestaurant.getRestaurantCusineType();
//                List<RestaurantType> oldRestaurantTypeList = new ArrayList<>();
//                oldRestaurantTypeList.addAll(oldRestaurantTypeSet);
//
//                List<String> oldCuisineNamesList = new ArrayList<>();
//                List<String> newCuisineNamesList = new ArrayList<>();
//
//                newCuisineNamesList.addAll(NewRestaurant.getRestaurantCusineType());
//                for(int i=0;i<oldRestaurantTypeList.size();i++)
//                {
//                    oldCuisineNamesList.add(oldRestaurantTypeList.get(i).getCuisine());
//                }
//
//                Set<RestaurantType> extraCuisines = new HashSet<>();
//                for(int i=0;i<oldCuisineNamesList.size();i++)
//                {
//                    if(!(newCuisineNamesList.contains(oldCuisineNamesList.get(i))))
//                    {
//                        restaurantTypeRepository.unAssignCuisineFromRestaurant(restaurantId,oldRestaurantTypeList.get(i).getRestaurantTypeId());
//                    }
//                }
//                for(int i=0;i<newCuisineNamesList.size();i++)
//                {
//                    if(!(oldCuisineNamesList.contains(newCuisineNamesList.get(i))))
//                    {
//                        RestaurantType newCuisine = restaurantTypeService.getRestaurantTypeByCuisineName(newCuisineNamesList.get(i));
//                        //OldRestaurant.setCuisineType(newCuisine);
//                        extraCuisines.add(newCuisine);
//                    }
//                }
//                OldRestaurant.setRestaurantCusineType(extraCuisines);
//            }
            if(NewRestaurant.getRestaurantCusineType().size()!=0)
            {
                Set<String> cuisineNamesSet = NewRestaurant.getRestaurantCusineType();
                List<String> cuisineNamesList = new ArrayList<>();
                cuisineNamesList.addAll(cuisineNamesSet);
                Set<RestaurantType> restaurantTypeSet = new HashSet<>();
                for (int i = 0; i < cuisineNamesList.size(); i++) {
                    RestaurantType restaurantType10 = restaurantTypeService.getRestaurantTypeByCuisineName(cuisineNamesList.get(i));
                    restaurantTypeSet.add(restaurantType10);
                }
                OldRestaurant.setRestaurantCusineType(restaurantTypeSet);
            }
            if(NewRestaurant.getDescription()!=null && OldRestaurant.getDescription()!=NewRestaurant.getDescription())
            {
                OldRestaurant.setDescription(NewRestaurant.getDescription());
            }
            if(NewRestaurant.getFeatures()!=null && OldRestaurant.getFeatures()!=NewRestaurant.getFeatures())
            {
                OldRestaurant.setFeatures(NewRestaurant.getFeatures());
            }
            if(NewRestaurant.getFullAddress()!=null && OldRestaurant.getFullAddress()!=NewRestaurant.getFullAddress())
            {
                OldRestaurant.setFullAddress(NewRestaurant.getFullAddress());
            }
            if(NewRestaurant.getTableCustomization()!=null && OldRestaurant.getTableCustomization()!= NewRestaurant.getTableCustomization())
            {
                OldRestaurant.setTableCustomization(NewRestaurant.getTableCustomization());
            }
            if(NewRestaurant.getPincode()!=null && OldRestaurant.getRestaurantLocation().getPincode()!=NewRestaurant.getPincode())
            {
                RestaurantLocation location = restaurantLocationRepository.findByPincode(NewRestaurant.getPincode());
                OldRestaurant.assignLocation(location);
            }
            if(NewRestaurant.getTag()!=null && OldRestaurant.getTag()!=NewRestaurant.getTag())
            {
                OldRestaurant.setTag(NewRestaurant.getTag());
            }
            if(NewRestaurant.getRestaurantContactNumber()!=null && OldRestaurant.getRestaurantContactNumber()!=NewRestaurant.getRestaurantContactNumber())
            {
                OldRestaurant.setRestaurantContactNumber(NewRestaurant.getRestaurantContactNumber());
            }
            if(NewRestaurant.getTimings()!=null && OldRestaurant.getTimings()!=NewRestaurant.getTimings())
            {
                OldRestaurant.setTimings(NewRestaurant.getTimings());
            }

            restaurantRepository.save(OldRestaurant);

            if(NewRestaurant.getRestaurantTable()!=null)
            {
                List<String> tableNames = new ArrayList<>();
                List<String> NewtableNames = new ArrayList<>();
                for(int i=0;i<OldRestaurant.getRestaurantTable().size();i++)
                {
                    tableNames.add(OldRestaurant.getRestaurantTable().get(i).getTableName());
                }
                for(int i=0;i<NewRestaurant.getRestaurantTable().size();i++)
                {
                    NewtableNames.add(NewRestaurant.getRestaurantTable().get(i).getTableName());
                }
                for(int i=0;i<NewRestaurant.getRestaurantTable().size();i++)
                {
                    RestaurantTable tableEntity = restaurantTableMapper.dtoToEntity(NewRestaurant.getRestaurantTable().get(i));
                    if(!(tableNames.contains(tableEntity.getTableName()))) {
                        tableEntity.setTheRestaurant(OldRestaurant);
                        restaurantTableRepository.save(tableEntity);
                    }
                    if(tableNames.contains(tableEntity.getTableName()))
                    {
                        RestaurantTable table = restaurantTableRepository.findByRestaurantIdAndTableName(OldRestaurant.getRestaurantId(),tableEntity.getTableName());
                        if(tableEntity.getSeatsCount()!=null && !(tableEntity.getSeatsCount().equals(table.getSeatsCount())))
                        {
                            table.setSeatsCount(tableEntity.getSeatsCount());
                        }
                        if(tableEntity.getCustomisationOptions()!=null && !(tableEntity.getCustomisationOptions().equals(table.getCustomisationOptions())))
                        {
                            table.setCustomisationOptions(tableEntity.getCustomisationOptions());
                        }
                        restaurantTableRepository.save(table);
                    }
                }
                for(int i=0;i<tableNames.size();i++)
                {
                    if(!(NewtableNames.contains(tableNames.get(i))))
                    {
                        RestaurantTable deleteTable = restaurantTableRepository.findByRestaurantIdAndTableName(OldRestaurant.getRestaurantId(),tableNames.get(i));
                        restaurantTableService.deleteRestaurantTableByTableId(deleteTable.getTableId());
                    }
                }
            }
            return ResponseEntity.ok("Restaurant details Updated");
        }
        else {
            throw new NotFoundException("Cant Update no restaurant exists with provided id : " + restaurantId);
            }
}

    @Override
    public List<RestaurantDto> getRestaurantByRestaurantType(RestaurantType restaurantType) {
        List<Restaurant> restaurant4 = restaurantRepository.findByRestaurantCusineType(restaurantType);
        if (restaurant4.isEmpty()) {
            throw new NotFoundException("Sorry No restaurants present with restaurant type : " + restaurantType);
        }
        return restaurantMapper.entityToDtos(restaurant4);
    }


    @Override
    public List<Restaurant> getByCity(String city, Pageable pageable) {
        return restaurantRepository.findByRestaurantLocationCityOrderByRatingAverageDesc(city, pageable);
    }

    @Override
    public List<Restaurant> getTop3(String city) {
        List<Restaurant> RestaurantList = restaurantRepository.findByRestaurantLocationCityOrderByRatingAverageDesc(city, Pageable.unpaged());
        List<Restaurant> top3 = new ArrayList<>();
        if(RestaurantList.size() < 4){return RestaurantList;}
        else{
            for(int i=1;i<=3;i++)
            {top3.add(RestaurantList.get(i));}
            return top3;
        }

    }

    @Override
    public List<RestaurantDto> getRestaurantsByCityAndCuisine(String city, String cuisine) {
        List<Restaurant> restaurant = restaurantRepository.findByRestaurantLocationCityAndRestaurantCusineTypeCuisineOrderByRatingAverageDesc(city, cuisine);
        return restaurantMapper.entityToDtos(restaurant);
    }

    @Override
    public List<String> getRestaurantNamesByCity(String city) {
        List<String> RestaurantNames = restaurantRepository.findRestaurantNameByRestaurantLocationCity(city);
        return RestaurantNames;}
}