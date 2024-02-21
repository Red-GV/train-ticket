package train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import train.entity.TrainType;
import train.repository.TrainTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainTypeRepository repository;


    @Override
    public boolean create(TrainType trainType, HttpHeaders headers) {
        boolean result = false;
        if (!repository.findById(trainType.getId()).isPresent()) {
            TrainType type = new TrainType(trainType.getId(), trainType.getEconomyClass(), trainType.getConfortClass());
            type.setAverageSpeed(trainType.getAverageSpeed());
            repository.save(type);
            result = true;
        }
        return result;
    }

    @Override
    public TrainType retrieve(String id, HttpHeaders headers) {
        Optional<TrainType> opTrainType = repository.findById(id);
        if (!opTrainType.isPresent()) {
            return null;
        } else {
            return opTrainType.get();
        }
    }

    @Override
    public boolean update(TrainType trainType, HttpHeaders headers) {
        Optional<TrainType> opTrainType = repository.findById(trainType.getId());
        if (opTrainType.isPresent()) {
            TrainType type = opTrainType.get();
            type.setEconomyClass(trainType.getEconomyClass());
            type.setConfortClass(trainType.getConfortClass());
            type.setAverageSpeed(trainType.getAverageSpeed());
            repository.save(type);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id, HttpHeaders headers) {
        Optional<TrainType> opTrainType = repository.findById(id);
        if (opTrainType.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<TrainType> query(HttpHeaders headers) {
        return repository.findAll();
    }

}
