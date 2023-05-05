package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.Connection;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystem;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class MachineCommander {

    private final MiningMachineRepository miningMachineRepository;
    private final FieldRepository fieldRepository;
    private final TransportSystemRepository transportSystemRepository;

    public MachineCommander(MiningMachineRepository miningMachineRepository, FieldRepository fieldRepository, TransportSystemRepository transportSystemRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.transportSystemRepository = transportSystemRepository;
    }

    public Boolean InsertMachine(UUID miningMachineId, Command commandString) {
        for (Field value : fieldRepository.findAll()) {
            if (value.getFieldID().toString().equals(commandString.getGridId().toString())) {
                for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
                    if(miningMachine.getField() != null) {
                        if (miningMachine.getField() == value.getFieldID()
                                && miningMachine.getPoint().getX() == 0
                                && miningMachine.getPoint().getY() == 0) {  //entrance ist belegt
                            return false;
                        }
                    }
                    if (miningMachine.getMiningMachineId() == miningMachineId) {  //enter field
                        miningMachine.setField(value.getFieldID());
                        miningMachine.setPoint(new Point(0,0));
                        miningMachineRepository.save(miningMachine);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Boolean TransportMachine(){
        for (TransportSystem value : transportSystemRepository.findAll()) {
            for (int x = 0; x < value.getConnections().size(); x++) {
                for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
                    if (miningMachine.getField() != null) {
                        if (transportConditions(miningMachine,value.getConnections().get(x))) {
                            miningMachine.setPoint(value.getConnections().get(x).getDestinationCoordinates());
                            miningMachine.setField(value.getConnections().get(x).getDestinationField().getFieldID());
                            miningMachineRepository.save(miningMachine);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    public Boolean executeCommand(UUID miningMachineId, Command commandString) {
        MiningMachine miningMachine = findMachine(miningMachineId);
        miningMachine.getCommands().add(commandString);
        switch (commandString.getCommandType()) {
            case ENTER:
                return InsertMachine(miningMachineId,commandString);
            case TRANSPORT:
                return TransportMachine();
            case WEST:
                for (int i = 0; i < commandString.getNumberOfSteps(); i++) {                            //movements happen one step at a time
                    if (miningMachine.getPoint().getX() == 0) break;                                    //break at 0 or else "Negative Numbers" Exception
                    if (!miningMachine.isBlocked((ArrayList<Field>) fieldRepository.findAll(), new Point(miningMachine.getPoint().getX() - 1, miningMachine.getPoint().getY())))
                        miningMachine.moveWest();
                }
                break;
            case SOUTH:
                for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                    if (miningMachine.getPoint().getY() == 0) break;                                    //break at 0 or else "Negative Numbers" Exception
                    if (!miningMachine.isBlocked((ArrayList<Field>) fieldRepository.findAll(), new Point(miningMachine.getPoint().getX(), miningMachine.getPoint().getY() - 1)))
                        miningMachine.moveSouth();
                }
                break;
            case EAST:
                for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                    if (!miningMachine.isBlocked((ArrayList<Field>) fieldRepository.findAll(), new Point(miningMachine.getPoint().getX() + 1, miningMachine.getPoint().getY())))
                        miningMachine.moveEast();
                }
                break;
            case NORTH:
                for (int i = 0; i < commandString.getNumberOfSteps(); i++) {
                    if (!miningMachine.isBlocked((ArrayList<Field>) fieldRepository.findAll() ,new Point(miningMachine.getPoint().getX(),miningMachine.getPoint().getY()+1)))
                        miningMachine.moveNorth();
                }
                break;
        }

        return false;
    }

    public boolean transportConditions(MiningMachine miningMachine, Connection connection){
        if(miningMachine.getFieldId().toString().equals(connection.getSourceField().getFieldID().toString())) System.out.println("machine is on connectionfield");
        else return false;
        if(miningMachine.getPoint().toString().equals(connection.getSourceCoordinates().toString())) System.out.println("machine is on correct coordinates");
        else return false;
        if(miningMachine.getPoint().toString().equals(connection.getSourceCoordinates().toString())) System.out.println("destination is mentioned in command");
        else return false;
        return true;
    }

    public MiningMachine findMachine(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
            if (miningMachine.getMiningMachineId() == miningMachineId) return miningMachine;
        }
        throw new RuntimeException ("machine not found");
    }
}

