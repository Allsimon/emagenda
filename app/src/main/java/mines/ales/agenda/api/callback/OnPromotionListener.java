package mines.ales.agenda.api.callback;

import java.util.List;

import mines.ales.agenda.api.pojo.Promotion;

public interface OnPromotionListener {
    void onPromotionsFound(List<Promotion> promotions);
}
