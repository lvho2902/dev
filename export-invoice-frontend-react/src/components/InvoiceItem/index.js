import classNames from 'classnames/bind';
import styles from './InvoiceItem.module.scss';

const cx = classNames.bind(styles);

function InvoiceItem() {
    return (
        <div className={cx('wrapper')}>
            <div className={cx('info')}>
                <h4 className={cx('name')}>
                    <span>Invoice ICON</span>
                </h4>
                <span className={cx('')}></span>
            </div>
        </div>
    );
}

export default InvoiceItem;
