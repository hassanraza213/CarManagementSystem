    package com.cms.carManagementSystem.service;

    import com.cms.carManagementSystem.dto.CarDTO;
    import com.cms.carManagementSystem.dto.DepartmentDTO;
    import com.cms.carManagementSystem.dto.FuelDTO;
    import com.cms.carManagementSystem.dto.MinistryDTO;
    import com.cms.carManagementSystem.entity.Car;
    import com.cms.carManagementSystem.entity.Department;
    import com.cms.carManagementSystem.entity.Fuel;
    import com.cms.carManagementSystem.repository.CarRepo;
    import com.cms.carManagementSystem.repository.FuelRepo;
    import jakarta.persistence.EntityNotFoundException;
    import lombok.extern.slf4j.Slf4j;
    import org.modelmapper.ModelMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    @Slf4j
    public class FuelService {

        @Autowired
        private final FuelRepo fuelRepo;
        private final ModelMapper modelMapper;
        private final CarRepo carRepo;

        public FuelService(FuelRepo fuelRepo, ModelMapper modelMapper, CarRepo carRepo) {
            this.fuelRepo = fuelRepo;
            this.modelMapper = modelMapper;
            this.carRepo = carRepo;
        }

        public FuelDTO createFuel(FuelDTO fuelDTO) {
            log.info("Creating fuel api with details: {}", fuelDTO);
            Car car = carRepo.findById(fuelDTO.getCarId()).orElseThrow(() -> {
                log.error("Car not found with Id: {}", fuelDTO.getCarId());
                return new EntityNotFoundException("car not found with Id: " + fuelDTO.getCarId());
            });

            Fuel convertFuelFromDTOToEntity = modelMapper.map(fuelDTO, Fuel.class);
            convertFuelFromDTOToEntity.setCar(car);

            log.info("Saving the Fuel details : {}", convertFuelFromDTOToEntity);
            Fuel savedFuel = fuelRepo.save(convertFuelFromDTOToEntity);

            FuelDTO convertFuelFromEntityToDTO = modelMapper.map(savedFuel, FuelDTO.class);
            convertFuelFromEntityToDTO.setCarDTO(modelMapper.map(car, CarDTO.class));
            log.info("Saved fuel details with Id: {}", savedFuel.getFuelId());
            return convertFuelFromEntityToDTO;
        }

    //    public FuelDTO updateFuelDetails(Long id, FuelDTO fuelDTO) {
    //        log.info("Updating fuel details: {}", fuelDTO);
    //        Car car = carRepo.findById(fuelDTO.getCarId()).orElseThrow(() -> {
    //            log.error("Car not found with Id: {}", fuelDTO.getCarId());
    //            return new EntityNotFoundException("Car not found with Id: " + fuelDTO.getCarId());
    //        });
    //
    //        Fuel updateFuel = fuelRepo.findById(id).orElseThrow(() -> {
    //            log.error("Fuel details not found with Id: {}", id);
    //            return new EntityNotFoundException("Fuel details not found with Id: " + id);
    //        });
    //
    //        log.info("Mapping FuelDTO to FuelEntity {}", fuelDTO);
    //        updateFuel.setFuelType(fuelDTO.getFuelType());
    //        updateFuel.setFuelQuantity(fuelDTO.getFuelQuantity());
    //        updateFuel.setDate(fuelDTO.getDate());
    //        updateFuel.setDescription(fuelDTO.getDescription());
    //        updateFuel.setCar(car);
    //
    //        log.info("Saving updated fuel details {}", updateFuel);
    //        Fuel savedFuel = fuelRepo.save(updateFuel);
    //
    //        FuelDTO convertFuelEntityToFuelDTO = modelMapper.map(updateFuel, FuelDTO.class);
    //        convertFuelEntityToFuelDTO.setCarDTO(modelMapper.map(car, CarDTO.class));
    //
    //        log.info("Fuel details updated with the Id: {}", updateFuel.getFuelId());
    //        return convertFuelEntityToFuelDTO;
    //    }

        public FuelDTO updateFuelDetails(Long id, FuelDTO fuelDTO) {
            log.info("Updating fuel details: {}", fuelDTO);

            // Fetch the existing Fuel entity
            Fuel updateFuel = fuelRepo.findById(id).orElseThrow(() -> {
                log.error("Fuel details not found with Id: {}", id);
                return new EntityNotFoundException("Fuel details not found with Id: " + id);
            });

            // Fetch the associated Car entity
            Car car = carRepo.findById(fuelDTO.getCarId()).orElseThrow(() -> {
                log.error("Car not found with Id: {}", fuelDTO.getCarId());
                return new EntityNotFoundException("Car not found with Id: " + fuelDTO.getCarId());
            });

            // Map non-null fields from FuelDTO to Fuel (excluding nested entities)
            modelMapper.getConfiguration().setSkipNullEnabled(true); // Skip null fields
            modelMapper.map(fuelDTO, updateFuel);

            // Set the Car association manually (to avoid cascading issues)
            updateFuel.setCar(car);

            // Save the updated Fuel entity
            Fuel savedFuel = fuelRepo.save(updateFuel);
            log.info("Fuel details updated with the Id: {}", savedFuel.getFuelId());

            // Map the updated Fuel entity to FuelDTO
            FuelDTO updatedFuelDTO = modelMapper.map(savedFuel, FuelDTO.class);

            // Manually set the carId in the FuelDTO
            updatedFuelDTO.setCarId(car.getCarId());

            // Map the associated Car entity to CarDTO
            updatedFuelDTO.setCarDTO(modelMapper.map(car, CarDTO.class));

            return updatedFuelDTO;
        }

        public FuelDTO getFuelDetailsById(Long id) {
            log.info("Fetching the fuel with Id: {}", id);
            Fuel fuelById = fuelRepo.findById(id).orElseThrow(() -> {
                log.error("Fuel not found with Id: {}", id);
                return new EntityNotFoundException("Fuel not found with Id: {} "+ id);
            });

            FuelDTO convertFuelEntityToFuelDTO = modelMapper.map(fuelById, FuelDTO.class);
            Car car = fuelById.getCar();
            CarDTO carDTO = modelMapper.map(car, CarDTO.class);
            Department department = car.getDepartment();
            DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
            departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
            carDTO.setDepartmentDTO(departmentDTO);
            convertFuelEntityToFuelDTO.setCarDTO(carDTO);

            log.info("Fuel details found with Quantity - {} and date - {}", fuelById.getFuelQuantity(), fuelById.getDate());
            return convertFuelEntityToFuelDTO;
        }

        public List<FuelDTO> getAllFuelDetails(){
            log.info("Fetching all fuel details");
            List<Fuel> fuelList = fuelRepo.findAll();
            List<FuelDTO> fuelDTOList = fuelList.stream().map( fuel -> {
                FuelDTO convertFuelEntityToFuelDTO = modelMapper.map(fuel, FuelDTO.class);
                Car car = fuel.getCar();
                CarDTO carDTO = modelMapper.map(car, CarDTO.class);
                Department department = car.getDepartment();
                DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
                departmentDTO.setMinistryDTO(modelMapper.map(department.getMinistry(), MinistryDTO.class));
                carDTO.setDepartmentDTO(departmentDTO);
                convertFuelEntityToFuelDTO.setCarDTO(carDTO);
                return convertFuelEntityToFuelDTO;
            }).collect(Collectors.toList());

            log.info("Retrieved {} fuel details",fuelList.size());
            return fuelDTOList;
        }

        public void deleteFuelDetailsById(Long id){
            log.info("Attempting to delete fuel details with Id: {}",id);
            Fuel deleteFuelDetails = fuelRepo.findById(id).orElseThrow(()-> {
                log.error("Fuel details not found with Id: {}",id);
                return new EntityNotFoundException("Fuel details not found with Id: "+id);
            });
            fuelRepo.delete(deleteFuelDetails);
            log.info("Fuel detials with Id {} deleted successfully",id);
        }
    }
