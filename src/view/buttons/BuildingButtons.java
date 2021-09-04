package view.buttons;

import java.util.ArrayList;

public class BuildingButtons {

    private final UpgradeButton archeryUpgradeButton;
    private final UpgradeButton barracksUpgradeButton;
    private final UpgradeButton stableUpgradeButton;
    private final UpgradeButton marketUpgradeButton;
    private final UpgradeButton farmUpgradeButton;
    private final BuyButton archeryBuyButton;
    private final BuyButton barracksBuyButton;
    private final BuyButton stableBuyButton;
    private final BuyButton marketBuyButton;
    private final BuyButton farmBuyButton;
    private final RecruitButton archeryRecruitButton;
    private final RecruitButton barracksRecruitButton;
    private final RecruitButton stableRecruitButton;
    private final ArrayList<UpgradeButton> upgradeButtons;
    private final ArrayList<RecruitButton> recruitButtons;
    private final ArrayList<BuyButton> buyButtons;
    private final CloseButton closeButton;

    public BuildingButtons() {
        this.upgradeButtons = new ArrayList<>();
        this.recruitButtons = new ArrayList<>();
        this.buyButtons = new ArrayList<>();
        archeryUpgradeButton = new UpgradeButton();
        barracksUpgradeButton = new UpgradeButton();
        stableUpgradeButton = new UpgradeButton();
        marketUpgradeButton = new UpgradeButton();
        farmUpgradeButton = new UpgradeButton();
        upgradeButtons.add(archeryUpgradeButton);
        upgradeButtons.add(barracksUpgradeButton);
        upgradeButtons.add(stableUpgradeButton);
        upgradeButtons.add(marketUpgradeButton);
        upgradeButtons.add(farmUpgradeButton);
        archeryBuyButton = new BuyButton();
        barracksBuyButton = new BuyButton();
        stableBuyButton = new BuyButton();
        marketBuyButton = new BuyButton();
        farmBuyButton = new BuyButton();
        buyButtons.add(archeryBuyButton);
        buyButtons.add(barracksBuyButton);
        buyButtons.add(stableBuyButton);
        buyButtons.add(marketBuyButton);
        buyButtons.add(farmBuyButton);
        archeryRecruitButton = new RecruitButton();
        barracksRecruitButton = new RecruitButton();
        stableRecruitButton = new RecruitButton();
        recruitButtons.add(archeryRecruitButton);
        recruitButtons.add(barracksRecruitButton);
        recruitButtons.add(stableRecruitButton);
        this.closeButton = new CloseButton();
    }

    public UpgradeButton getArcheryUpgradeButton() {
        return archeryUpgradeButton;
    }

    public UpgradeButton getBarracksUpgradeButton() {
        return barracksUpgradeButton;
    }

    public UpgradeButton getStableUpgradeButton() {
        return stableUpgradeButton;
    }

    public UpgradeButton getMarketUpgradeButton() {
        return marketUpgradeButton;
    }

    public CustomButton getCloseButton() {
        return closeButton;
    }

    public UpgradeButton getFarmUpgradeButton() {
        return farmUpgradeButton;
    }

    public BuyButton getArcheryBuyButton() {
        return archeryBuyButton;
    }

    public BuyButton getBarracksBuyButton() {
        return barracksBuyButton;
    }

    public BuyButton getStableBuyButton() {
        return stableBuyButton;
    }

    public BuyButton getMarketBuyButton() {
        return marketBuyButton;
    }

    public BuyButton getFarmBuyButton() {
        return farmBuyButton;
    }

    public RecruitButton getArcheryRecruitButton() {
        return archeryRecruitButton;
    }

    public RecruitButton getBarracksRecruitButton() {
        return barracksRecruitButton;
    }

    public RecruitButton getStableRecruitButton() {
        return stableRecruitButton;
    }

    public ArrayList<UpgradeButton> getUpgradeButtons() {
        return upgradeButtons;
    }

    public ArrayList<RecruitButton> getRecruitButtons() {
        return recruitButtons;
    }

    public ArrayList<BuyButton> getBuyButtons() {
        return buyButtons;
    }
}
