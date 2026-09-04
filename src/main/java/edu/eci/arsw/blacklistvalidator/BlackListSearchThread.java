package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackListSearchThread extends Thread {
    private final int startIndex;
    private final int endIndex;
    private final String ipaddress;
    private final AtomicInteger occurrencesCount;
    private final List<Integer> blackListOccurrences;
    private final AtomicBoolean alarmTriggered;
    private final int alarmCount;

    public BlackListSearchThread(int startIndex, int endIndex, String ipaddress, AtomicInteger occurrencesCount, List<Integer> blackListOcurrences, AtomicBoolean alarmTriggered, int alarmCount){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.ipaddress = ipaddress;
        this.occurrencesCount = occurrencesCount;
        this.blackListOccurrences = blackListOcurrences;
        this.alarmTriggered = alarmTriggered;
        this.alarmCount = alarmCount;
    }

    @Override
    public void run(){
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        for (int i = startIndex; i < endIndex; i++){
            if (alarmTriggered.get()){
                break;
            }
            if (skds.isInBlackListServer(i, ipaddress)){
                blackListOccurrences.add(i);
                int current = occurrencesCount.incrementAndGet();

                if (current >= alarmCount){
                    alarmTriggered.set(true);
                }
            }
        }
    }
}
